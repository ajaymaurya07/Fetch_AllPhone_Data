package com.example.contact.dataclass

data class MessageModel(
    val id: Long,
    val address: String,
    val body: String,
    val date: Long,
    val type: Int
)
