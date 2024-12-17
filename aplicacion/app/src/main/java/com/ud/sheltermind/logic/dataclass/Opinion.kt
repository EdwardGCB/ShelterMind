package com.ud.sheltermind.logic.dataclass

data class Opinion(
    val client: Client? = null,
    val clasification: Float = 0f,
    val description: String = ""
)