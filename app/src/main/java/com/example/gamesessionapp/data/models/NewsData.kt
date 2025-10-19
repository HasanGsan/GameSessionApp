package com.example.gamesessionapp.data.models

data class NewsData (
    val id: String = "",
    val title: String = "",
    val preview: Int,
    val content: String = "",
    val timeCreated: Int = 0,
    val postCategory: String = "",
    val tags: String = ""
)