package com.ud.sheltermind.logic.dataclass

data class Psychologist(
    val user: User? = null,
    val license: String = "",
    val specialists: List<String> = emptyList(),
    val qualification: Float =  0f,
    val opinions: List<Opinion> = emptyList(),
    val description: String
)