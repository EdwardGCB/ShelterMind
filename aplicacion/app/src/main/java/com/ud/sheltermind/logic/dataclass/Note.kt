package com.ud.sheltermind.logic.dataclass

import kotlin.annotation.Target

data class Note(
    val creationTime: Long = 0,
    val creationDate: Long = 0,
    val state: State? = null,
    val target: Target? = null,
    val description: String = ""

)
