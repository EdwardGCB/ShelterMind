package com.ud.sheltermind.logic.dataclass

data class Syntom(
    val id: String = "",
    val name: String = "",
    val value: Int = 0,
    val advices: List<Advice> = emptyList(),
    val targets: List<Target> = emptyList()
)
