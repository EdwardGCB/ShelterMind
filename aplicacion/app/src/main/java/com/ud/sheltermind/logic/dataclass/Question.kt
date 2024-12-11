package com.ud.sheltermind.logic.dataclass

data class Question(
    val id: String = "",
    val number: Int = 0,
    val text: String = "",
    val options: List<Answer> = emptyList()
)