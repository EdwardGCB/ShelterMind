package com.ud.sheltermind.views.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ud.sheltermind.R
import com.ud.sheltermind.logic.dataclass.Syntom
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

    private val _isUserDataFetched = MutableStateFlow(false)
    val isUserDataFetched: StateFlow<Boolean> = _isUserDataFetched

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    // Listener de autenticación
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser
        _currentUser.value = user
        _isLoggedIn.value = user != null
        if (user != null && !_isUserDataFetched.value) {
            fetchUserDataFromFirestore(user.uid)
        }
    }

    init {
        // Agregar listener de autenticación
        auth.addAuthStateListener(authStateListener)
    }

    fun addAuthStateListener() {
        auth.addAuthStateListener(authStateListener)
    }

    fun removeAuthStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    fun updateUser(field: String, value: String, onComplete: () -> Unit) {
        val user = _userData.value ?: return
        val userRef = db.collection("users").document(user.id)
        val updates = mapOf(field to value)
        userRef.update(updates)
            .addOnSuccessListener {
                _userData.value = _userData.value?.copy(
                    name = if (field == "name") value else user.name,
                    type = if (field == "type") value else user.type,
                    email = if (field == "email") value else user.email,
                    number = if (field == "number") value else user.number,
                    syntomValue = if (field == "syntomValue") value.toDouble() else user.syntomValue,
                    lastQuestion = if (field == "lastQuestion") value.toInt() else user.lastQuestion,
                    notifications = if (field == "notifications") value.toBoolean() else user.notifications
                )
                _errorMessage.value = null
                onComplete()
            } .addOnFailureListener { ex ->
                _errorMessage.value = "Error al actualizar la información del usuario: ${ex.message}"
            }
    }

    // Obtener información del usuario desde Firestore
    private fun fetchUserDataFromFirestore(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _userData.value = document.toObject(User::class.java)
                    _isUserDataFetched.value = true
                } else {
                    _errorMessage.value = "Datos del usuario no encontrados."
                }
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al cargar los datos del usuario: ${ex.message}"
            }
    }

    fun updateUserAnswer(user: User, nroQuestion: Int, value: Double){
        val userRef = db.collection("users").document(user.id)
        user.syntomValue += value
        Log.d("userValue", user.syntomValue.toString())
        Log.d("syntomValue", value.toString())
        val updates = mapOf(
            "lastQuestion" to nroQuestion,
            "syntomValue" to user.syntomValue
        )
        userRef.update(updates)
            .addOnFailureListener {
                _errorMessage.value = "Error al actualizar la informacion"
            }
    }

    // Iniciar sesión con Google
    fun signInWithGoogle(credential: AuthCredential, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result.user?.uid ?: return@addOnCompleteListener
                        if (!_isUserDataFetched.value) {
                            fetchUserDataFromFirestore(userId)
                        }
                        _isLoggedIn.value = true
                        home()
                    }
                }
                .addOnFailureListener { ex ->
                    _errorMessage.value = ex.message
                }
        } catch (ex: Exception) {
            _errorMessage.value = ex.message
        }
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.token))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, options)
    }

    // Registro con email y contraseña
    fun signUpWithEmail(user: User, verifyPassword: String) {
        if (!validateFields(user)) return

        if (user.password != verifyPassword) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid ?: return@addOnSuccessListener
                if (!_isUserDataFetched.value) {
                    saveUserDataToFirestore(userId, user)
                }
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = ex.message
            }
    }

    // Guardar información en Firestore
    private fun saveUserDataToFirestore(userId: String, user: User) {
        val userRef = db.collection("users").document(userId)
        val userData = mapOf(
            "id" to userId,
            "name" to user.name,
            "type" to user.type,
            "email" to user.email,
            "number" to user.number,
            "syntomValue" to user.syntomValue,
            "lastQuestion" to user.lastQuestion,
            "notifications" to user.notifications
        )
        userRef.set(userData)
            .addOnSuccessListener {
                _isLoggedIn.value = true
                fetchUserDataFromFirestore(userId)
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al guardar la información: ${ex.message}"
            }
    }

    // Iniciar sesión con email y contraseña
    fun loginWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor ingresa tanto el correo como la contraseña."
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid ?: return@addOnSuccessListener
                if (!_isUserDataFetched.value) {
                    fetchUserDataFromFirestore(userId)
                }
                _isLoggedIn.value = true
                _errorMessage.value = null
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = ex.message
            }
    }

    // Cerrar sesión
    fun signOut() {
        auth.signOut()
        _isLoggedIn.value = false
        _currentUser.value = null
        _userData.value = null
        _isUserDataFetched.value = false
    }

    // Validar campos
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

    override fun onCleared() {
        super.onCleared()
        removeAuthStateListener()
    }
}
