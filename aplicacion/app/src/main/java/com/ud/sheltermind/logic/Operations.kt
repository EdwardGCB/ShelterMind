package com.ud.sheltermind.logic

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

open class Operations {
    @RequiresApi(Build.VERSION_CODES.O)
    open fun obtenerFechaActual(): LocalDate {
        return LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun obtenerFechaHoraActual(): LocalDateTime {
        return LocalDateTime.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun obtenerMesDiaActual(fechaActual: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM dd")
        return fechaActual.format(formatter).toUpperCase(Locale.ROOT)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun obtenerMes(fechaActual: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM")
        return fechaActual.format(formatter).toUpperCase(Locale.ROOT)
    }


}