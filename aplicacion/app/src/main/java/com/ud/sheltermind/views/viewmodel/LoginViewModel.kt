package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    //Login authentication
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val auth: FirebaseAuth = Firebase.auth

    fun singInWithGoogle(credential: AuthCredential, home: () ->Unit) = viewModelScope.launch{
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        _isLoggedIn.value = true
                        home()
                    }
                }
                .addOnFailureListener { ex->
                    _errorMessage.value = ex.message
                }
        }
        catch (ex: Exception) {
            _errorMessage.value = ex.message
        }
    }

    fun signUpWithEmail(user: User, verifyPassword: String) {
        if (!validateFields(user)) return

        if (user.password != verifyPassword) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        // Firebase Auth para crear un nuevo usuario
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let { firebaseUser ->
                    saveUserDataToFirestore(firebaseUser.uid, user)
                } ?: run {
                    _errorMessage.value = "Error: Usuario no encontrado después de la autenticación."
                }
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = ex.toString()
            }
    }

    // Función para validar campos vacíos
    private fun validateFields(user: User): Boolean {
        return when {
            user.name.isEmpty() -> {
                _errorMessage.value = "El nombre es obligatorio"
                false
            }
            user.email.isEmpty() -> {
                _errorMessage.value = "El email es obligatorio"
                false
            }
            user.password.isEmpty() -> {
                _errorMessage.value = "La contraseña es obligatoria"
                false
            }
            user.number.isEmpty() -> {
                _errorMessage.value = "El número es obligatorio"
                false
            }
            else -> true
        }
    }

    // Guardar información adicional en Firestore
    private fun saveUserDataToFirestore(userId: String, user: User) {
        val userRef = Firebase.firestore.collection("users").document(userId)
        val userData = mapOf(
            "id" to userId,
            "name" to user.name,
            "type" to user.type,
            "email" to user.email,
            "password" to user.password,
            "number" to user.number,
            "notifications" to user.notifications
        )
        userRef.set(userData)
            .addOnSuccessListener {
                _errorMessage.value = null
                _isLoggedIn.value = true
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al guardar la información del usuario: ${ex.message}"
            }
    }

    fun loginWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor ingresa tanto el correo como la contraseña."
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _isLoggedIn.value = true
                    _errorMessage.value = null
                }
                .addOnFailureListener {
                        ex -> _errorMessage.value = ex.message
                }
        }
    }
}