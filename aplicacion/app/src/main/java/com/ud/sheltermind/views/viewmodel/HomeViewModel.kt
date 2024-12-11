package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _userType = MutableStateFlow<String>("Cliente") // Valor por defecto
    val userType: StateFlow<String> get() = _userType

    // Método para actualizar el tipo de usuario
    fun setUserType(newType: String) {
        _userType.value = newType
    }
}
