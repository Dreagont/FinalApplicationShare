package com.example.finalapplicationshare.models

data class Topic(
    val id: String,
    val title: String,
    val visibility: String,
    val description: String,
    val folderId: String = "No",
    val words: Map<String, Word> = emptyMap()
)
