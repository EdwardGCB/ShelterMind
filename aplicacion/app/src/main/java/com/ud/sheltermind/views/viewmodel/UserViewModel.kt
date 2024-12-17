package com.ud.sheltermind.views.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
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
import com.ud.sheltermind.logic.dataclass.Client
import com.ud.sheltermind.logic.dataclass.Note
import com.ud.sheltermind.logic.dataclass.Opinion
import com.ud.sheltermind.logic.dataclass.Psychologist
import com.ud.sheltermind.logic.dataclass.State
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

    private val _isUserDataFetched = MutableStateFlow(false)
    val isUserDataFetched: StateFlow<Boolean> = _isUserDataFetched

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading


    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    private val _clientData = MutableStateFlow<Client?>(null)
    val clientData: StateFlow<Client?> = _clientData

    private val _psychologistData = MutableStateFlow<Psychologist?>(null)
    val psychologistData: StateFlow<Psychologist?> = _psychologistData

    // Listener de autenticación
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        Log.d("UserViewModel", "2")
        val user = firebaseAuth.currentUser
        Log.d("UserViewModel", user.toString())
        if (user != null) {
            Log.d("UserViewModel", "3")
            _currentUser.value = user
            _isLoggedIn.value = true
            if (!_isUserDataFetched.value) {
                Log.d("UserViewModel", "4")
                fetchUserDataFromFirestore(user.uid)
            }
        } else {
            _isLoggedIn.value = false
            _currentUser.value = null
            _userData.value = null
            _isUserDataFetched.value = false
        }
    }

    init {
        // Agregar listener de autenticación
        auth.addAuthStateListener(authStateListener)
    }

    fun addAuthStateListener() {
        if (_currentUser.value == null) {
            auth.addAuthStateListener(authStateListener)
            Log.d("UserViewModel", "AuthStateListener added")
        }
    }

    fun removeAuthStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    fun updateUser(field: String, value: String, onComplete: () -> Unit) {
        val user = _userData.value ?: return
        val updates = mapOf(field to value)
        updateUserDataInFirestore(user.id, updates) {
            _userData.value = user.copy(
                name = if (field == "name") value else user.name,
                type = if (field == "type") value else user.type,
                email = if (field == "email") value else user.email,
                number = if (field == "number") value else user.number,
                notifications = if (field == "notifications") value.toBoolean() else user.notifications
            )
            onComplete()
        }
    }

    fun updateUserAnswer(user: Client, nroQuestion: Int, value: Double) {
        user.syntomValue += value
        val updates = mapOf(
            "lastQuestion" to nroQuestion,
            "syntomValue" to user.syntomValue
        )
        updateUserDataInFirestore(user.user!!.id, updates)
    }


    // Iniciar sesión con Google
    fun signInWithGoogle(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                _isLoggedIn.value = true
            }
            .addOnFailureListener {
                _errorMessage.value = it.message
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
                    saveUserDataToFirestore(userId, user.type, user)
                }
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = ex.message
            }
    }

    // Iniciar sesión con email y contraseña
    fun loginWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor ingresa tanto el correo como la contraseña."
            return
        }

        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _isLoggedIn.value = true
                _errorMessage.value = null
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = ex.message
            }
            .addOnCompleteListener {
                _isLoading.value = false
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

    // Obtener información del usuario desde Firestore
    private fun fetchUserDataFromFirestore(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userType = document.getString("type") ?: ""

                    when (userType) {
                        "Cliente" -> {
                            // Crear la instancia de Client
                            val client = Client(
                                user = User(
                                    id = document.getString("id") ?: "",
                                    name = document.getString("name") ?: "",
                                    type = document.getString("type") ?: "",
                                    email = document.getString("email") ?: "",
                                    number = document.getString("number") ?: "",
                                    notifications = document.getBoolean("notifications") ?: false
                                ),
                                notes = (document.get("notes") as? List<Map<String, Any>>)?.map { noteMap ->
                                    Note(
                                        creationTime = (noteMap["creationTime"] as? Long) ?: 0L,
                                        creationDate = (noteMap["creationDate"] as? Long) ?: 0L,
                                        state = noteMap["state"] as? State,
                                        target = noteMap["target"] as? Target,
                                        description = noteMap["description"] as? String ?: ""
                                    )
                                } ?: emptyList(),
                                syntomValue = document.getDouble("syntomValue") ?: 0.0,
                                lastQuestion = document.getLong("lastQuestion")?.toInt() ?: 0
                            )
                            // Asignar el Client a _clientData
                            _clientData.value = client
                            _userData.value = client.user  // Opcional, para asignar también el User básico
                            Log.d("Firestore", "Client data fetched manually: $client")
                        }
                        "Psicologo" -> {
                            // Crear la instancia de Psychologist
                            val psychologist = Psychologist(
                                user = User(
                                    id = document.getString("id") ?: "",
                                    name = document.getString("name") ?: "",
                                    email = document.getString("email") ?: "",
                                    number = document.getString("number") ?: "",
                                    notifications = document.getBoolean("notifications") ?: false
                                ),
                                license = document.getString("license") ?: "",
                                specialists = document.get("specialists") as? List<String> ?: emptyList(),
                                qualification = document.getDouble("qualification")?.toFloat() ?: 0f,
                                opinions = (document.get("opinions") as? List<Map<String, Any>>)?.map { opinionMap ->
                                    Opinion(
                                        client = if (opinionMap["client"] != null) {
                                            // Convertir el cliente a tipo Client?
                                            Client(
                                                user = User(
                                                    id = opinionMap["client_id"] as? String ?: "",
                                                    name = opinionMap["client_name"] as? String ?: "",
                                                    type = document.getString("type") ?: "",
                                                    email = opinionMap["client_email"] as? String ?: "",
                                                    number = opinionMap["client_number"] as? String ?: "",
                                                    notifications = opinionMap["client_notifications"] as? Boolean ?: false
                                                ),
                                                notes = emptyList(),
                                                syntomValue = 0.0,
                                                lastQuestion = 0
                                            )
                                        } else {
                                            null
                                        },
                                        clasification = (opinionMap["clasification"] as? Float) ?: 0f,
                                        description = opinionMap["description"] as? String ?: ""
                                    )
                                } ?: emptyList(),
                                description = document.getString("description") ?: ""
                            )
                            // Asignar el Psychologist a _psychologistData
                            _psychologistData.value = psychologist
                            _userData.value = psychologist.user  // Opcional, para asignar también el User básico
                            Log.d("Firestore", "Psychologist data fetched manually: $psychologist")
                        }
                        else -> {
                            // Caso por defecto: tipo User genérico
                            val user = User(
                                id = document.getString("id") ?: "",
                                name = document.getString("name") ?: "",
                                type = document.getString("type") ?: "",
                                email = document.getString("email") ?: "",
                                number = document.getString("number") ?: "",
                                notifications = document.getBoolean("notifications") ?: false
                            )
                            // Asignar el User genérico a _userData
                            _userData.value = user
                            Log.d("Firestore", "Generic user data fetched manually: $user")
                        }
                    }
                    _isUserDataFetched.value = true
                } else {
                    _errorMessage.value = "Datos del usuario no encontrados."
                    Log.e("Firestore", "User document does not exist")
                }
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al cargar los datos del usuario: ${ex.message}"
                Log.e("Firestore", "Error fetching user data: ${ex.message}")
            }
    }

    private fun updateUserDataInFirestore(userId: String, updates: Map<String, Any>, onSuccess: () -> Unit = {}) {
        db.collection("users").document(userId)
            .update(updates)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al actualizar la información: ${ex.message}"
            }
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

    // Guardar información en Firestore
    private fun saveUserDataToFirestore(userId: String, userType: String, user: Any) {
        val userRef = db.collection("users").document(userId)
        val userData = mutableMapOf<String, Any>(
            "id" to userId,
            "type" to userType
        )

        // Llenar datos según el tipo de usuario
        when (user) {
            is Client -> {
                userData["name"] = user.user?.name ?: ""
                userData["email"] = user.user?.email ?: ""
                userData["number"] = user.user?.number ?: ""
                userData["notifications"] = user.user?.notifications ?: false
                //userData["notes"] = user.notes.map { note -> note.toMap() } // Conversión de notas a Map
                userData["syntomValue"] = user.syntomValue
                userData["lastQuestion"] = user.lastQuestion
            }
            is Psychologist -> {
                userData["name"] = user.user?.name ?: ""
                userData["email"] = user.user?.email ?: ""
                userData["number"] = user.user?.number ?: ""
                userData["notifications"] = user.user?.notifications ?: false
                userData["license"] = user.license
                userData["specialists"] = user.specialists
                userData["qualification"] = user.qualification
                //userData["opinions"] = user.opinions.map { opinion -> opinion.toMap() } // Conversión de opiniones
                userData["description"] = user.description
            }
            is User -> { // Si el usuario es tipo general (User)
                userData["name"] = user.name
                userData["email"] = user.email
                userData["number"] = user.number
                userData["notifications"] = user.notifications
            }
            else -> {
                _errorMessage.value = "Tipo de usuario no soportado"
                return
            }
        }

        // Guardar en Firestore
        userRef.set(userData)
            .addOnSuccessListener {
                _isLoggedIn.value = true
                fetchUserDataFromFirestore(userId)
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al guardar la información: ${ex.message}"
            }
    }


    private fun handleError(message: String, exception: Exception? = null) {
        Log.e("UserViewModel", message, exception)
        _errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        if (authStateListener != null) {
            removeAuthStateListener()
        }
    }

}
