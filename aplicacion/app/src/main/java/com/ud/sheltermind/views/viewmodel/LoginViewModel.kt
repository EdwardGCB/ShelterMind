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
    val _loading = MutableLiveData(false)

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
        if(user.username.isEmpty() || user.email.isEmpty() || user.password.isEmpty() || user.number.isEmpty()) {
            _errorMessage.value = "Todos los campos son obligatorios"
        } else {
            if(user.password == verifyPassword){
                // Comienza la autenticación con FirebaseAuth
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
                    .addOnSuccessListener { authResult ->
                        // Cuando el evento se ha realizado con normalidad
                        val firebaseUser = authResult.user
                        if (firebaseUser != null) {
                            val userId = firebaseUser.uid
                            // Guarda la información adicional del usuario en Firestore
                            val userRef = Firebase.firestore.collection("users").document(userId)
                            val userData = mapOf(
                                "userId" to userId,
                                "username" to user.username,
                                "userType" to user.userType,
                                "email" to user.email,
                                "password" to user.password,
                                "number" to user.number
                            )
                            userRef.set(userData)
                                .addOnSuccessListener {
                                    _errorMessage.value = null
                                    _isLoggedIn.value = true
                                }
                                .addOnFailureListener { ex ->
                                    _errorMessage.value = "Error al guardar información adicional: ${ex.message}"
                                }
                        } else {
                            _errorMessage.value = "Error: usuario no encontrado después de la autenticación."
                        }
                    }
                    .addOnFailureListener { ex ->
                        _errorMessage.value = ex.message
                    }
            }else{
                _errorMessage.value = "Las contraseñas no coinciden"
            }
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