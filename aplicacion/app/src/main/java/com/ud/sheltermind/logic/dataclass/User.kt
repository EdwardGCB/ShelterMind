package com.ud.sheltermind.logic.dataclass

data class User(
    val id: String ="",
    val name: String ="",
    val type: String ="",
    val email: String ="",
    val password: String ="",
    val number: String ="",
    val notifications: Boolean = true
)
