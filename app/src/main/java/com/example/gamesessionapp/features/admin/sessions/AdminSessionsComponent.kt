package com.example.gamesessionapp.features.admin.sessions

import android.util.Log.e
import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.repository.user.RoomUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdminSessionsComponent(
    componentContext: ComponentContext,
    private val userRepository : RoomUserRepository
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(AdminSessionsState())
    val state: StateFlow<AdminSessionsState> = _state.asStateFlow()

    private val coroutineScope = kotlinx.coroutines.MainScope()

    init {
        loadSessions()
        loadAvailableUsers()
    }

    private fun loadSessions() {
        coroutineScope.launch {
            userRepository.getAllSessionsWithUser().collect { sessions ->
                val sessionsWithUsers = mutableListOf<SessionWithUsers>()
                sessions.forEach { sessionWithUsers  ->
                    val users = userRepository.getUserForSession(sessionWithUsers.session.id)
                    sessionsWithUsers.add(sessionWithUsers.copy(users = users))
                }
                _state.value = _state.value.copy(sessions = sessionsWithUsers)
            }
        }
    }

    private fun loadAvailableUsers() {
        coroutineScope.launch {
            val users = userRepository.getNonAdminUsers()
            _state.value = _state.value.copy(availableUsers = users)
        }
    }

    fun onIntent(intent: AdminSessionsIntent) {
        when (intent) {
            AdminSessionsIntent.OpenCreateSessionDialog -> openCreateSessionDialog()
            AdminSessionsIntent.CloseCreateSessionDialog -> closeCreateSessionDialog()
            is AdminSessionsIntent.UpdateDate -> updateDate(intent.date)
            is AdminSessionsIntent.UpdateTime -> updateTime(intent.time)
            is AdminSessionsIntent.UpdateDuration -> updateDuration(intent.duration)
            is AdminSessionsIntent.ToggleUserSelection -> toggleUserSelection(intent.userLogin)
            AdminSessionsIntent.CreateSession -> createSession()
        }
    }

    private fun openCreateSessionDialog() {
        _state.value = _state.value.copy(showCreateSessionDialog = true)
    }

    private fun closeCreateSessionDialog() {
        _state.value = _state.value.copy(
            showCreateSessionDialog = false,
            date = "",
            time = "",
            duration = "",
            selectedUsers = emptySet(),
            isFormValid = false
        )
    }

    private fun updateDate(date: String) {
        _state.value = _state.value.copy(date = date)
        validateForm()
    }

    private fun updateTime(time: String){
        _state.value = _state.value.copy(time = time)
        validateForm()
    }

    private fun updateDuration(duration: String) {
        _state.value = _state.value.copy(duration = duration)
        validateForm()
    }

    private fun toggleUserSelection(userLogin: String) {
        val selectedUsers = _state.value.selectedUsers.toMutableSet()
        if(selectedUsers.contains(userLogin)){
            selectedUsers.remove(userLogin)
        } else {
            selectedUsers.add(userLogin)
        }

        _state.value = _state.value.copy(selectedUsers = selectedUsers)
        validateForm()
    }

    private fun validateForm() {
        val currentState = _state.value
        val isTimeValid = currentState.time.matches(Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"))
        val isDateValid = currentState.date.matches(Regex("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$"))
        val isDurationValid = currentState.duration.toIntOrNull()?.let { it > 0 && it <= 24 } ?: false
        val isValid = currentState.date.isNotBlank() &&
                currentState.time.isNotBlank() &&
                currentState.duration.isNotBlank() &&
                isTimeValid &&
                isDateValid &&
                isDurationValid &&
                currentState.selectedUsers.isNotEmpty()

        _state.value = currentState.copy(isFormValid = isValid)
    }

    private fun createSession() {
        coroutineScope.launch {
            try {
                val currentState = _state.value
                val startTime = parseDateTime(currentState.date, currentState.time)
                val endTime = startTime.plusHours(currentState.duration.toLong())
                val timeRange = "${currentState.time} - ${formatTime(endTime)}"

                userRepository.createSession(
                    date = formatDate(startTime),
                    timeRange = timeRange,
                    startTime = startTime,
                    duration = currentState.duration.toInt(),
                    userLogins = currentState.selectedUsers.toList()
                )

                closeCreateSessionDialog()
                loadSessions()

            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseDateTime(date: String, time: String) : LocalDateTime {
        val timeParts = time.split(":")
        val dateParts = date.split(":")

        return if (timeParts.size == 2 && dateParts.size == 3){
            try {
                LocalDateTime.of(
                    dateParts[2].toInt(),
                    dateParts[1].toInt(),
                    dateParts[0].toInt(),
                    timeParts[0].toInt(),
                    timeParts[1].toInt(),
                )
            } catch (e: Exception) {
                LocalDateTime.now()
            }
        } else {
            LocalDateTime.now()
        }
    }

    private fun formatTime(time: LocalDateTime): String {
        return DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()).format(time)
    }

    private fun formatDate(time: LocalDateTime): String {
        val now = LocalDateTime.now()
        val today = now.toLocalDate()
        val sessionDate = time.toLocalDate()

        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
        val dateString = time.format(formatter).uppercase()

        return when {
            sessionDate == today -> "СЕГОДНЯ, $dateString"
            sessionDate == today.minusDays(1) -> "ВЧЕРА, $dateString"
            else -> dateString
        }

    }



}