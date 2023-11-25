package com.example.finalapplicationshare

data class Topic(
    val id: String,
    val title: String,
    val visibility: String,
    val words: Map<String, Word> = emptyMap()
)