package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    // StateFlows para los campos de usuario
    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // Cargar datos del usuario actual desde Firestore
    fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            _loading.value = true
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        if (user != null) {
                            _userState.value = user
                        }
                    } else {
                        _errorMessage.value = "Usuario no encontrado."
                    }
                }
                .addOnFailureListener { ex ->
                    _errorMessage.value = "Error al cargar datos: ${ex.message}"
                }
                .addOnCompleteListener {
                    _loading.value = false
                }
        } else {
            _errorMessage.value = "Usuario no autenticado."
        }
    }

    // Actualizar datos del usuario en Firestore
    fun updateUserData(user: User) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            _loading.value = true
            firestore.collection("users").document(userId).set(user)
                .addOnSuccessListener {
                    _errorMessage.value = null
                }
                .addOnFailureListener { ex ->
                    _errorMessage.value = "Error al actualizar datos: ${ex.message}"
                }
                .addOnCompleteListener {
                    _loading.value = false
                }
        } else {
            _errorMessage.value = "Usuario no autenticado."
        }
    }
}
