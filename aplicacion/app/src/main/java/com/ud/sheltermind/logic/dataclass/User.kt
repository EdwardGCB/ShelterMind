package com.ud.sheltermind.logic.dataclass

data class User(
    val id: String ="",
    val name: String ="",
    val type: String ="",
    val email: String ="",
    val password: String ="",
    val number: String ="",
    val syntomValue: Float = 0f,
    val lastQuestion: Int = 0,
    val notifications: Boolean = true//
)
