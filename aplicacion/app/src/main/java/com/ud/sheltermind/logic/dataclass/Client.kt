package com.ud.sheltermind.logic.dataclass

data class Client (
    val user: User? = null,
    val notes : List<Note> = emptyList(),
    var syntomValue: Double = 0.0,
    val lastQuestion: Int = 0
)
