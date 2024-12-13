package com.ud.sheltermind.logic.dataclass

data class State(
    val id: String = "",
    val name: String = "",
    val icon: String ="",
    val color: String = "",
    val targets: List<Target> = emptyList()
)
