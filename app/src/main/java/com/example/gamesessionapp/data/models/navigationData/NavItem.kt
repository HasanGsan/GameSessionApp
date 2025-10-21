package com.example.gamesessionapp.data.models.navigationData

data class NavItem (
    val selected: Boolean,
    val onClick: () -> Unit,
    val iconRes: Int,
    val label: String
)