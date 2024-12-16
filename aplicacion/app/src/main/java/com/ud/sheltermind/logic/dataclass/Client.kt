package com.ud.sheltermind.logic.dataclass

data class Client (
    val user: User? = null,
    val notes : List<Note> = emptyList()
    val syntomValue: Double,
    val lastQuestion: Int
)
