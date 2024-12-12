package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private var syntomListener: ListenerRegistration? = null
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()


    //private val _syntoms = MutableStateFlow<List<Syntom>>(emptyList())
    //val players: StateFlow<List<Syntom>> = _syntoms

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    //validar el tipo de usuario logueado
    private val _userType = MutableStateFlow("")
    val userType: StateFlow<String> = _userType

    init {
        fetchUserType()
    }

    private fun fetchUserType() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    val type = document.getString("type") ?: "Cliente" // Valor predeterminado
                    _userType.value = type
                }
                .addOnFailureListener {
                    _userType.value = "Cliente" // Predeterminado en caso de error
                }
        }
    }


    //listar los usuarios de tipo clientes:
    private val _clients = MutableStateFlow<List<User>>(emptyList())
    val clients: StateFlow<List<User>> = _clients

    init {
        fetchClients()
    }

    private fun fetchClients() {
        db.collection("users")
            .whereEqualTo("type", "Cliente") // Filtro para usuarios de tipo "Cliente"
            .get()
            .addOnSuccessListener { documents ->
                val clientList = documents.mapNotNull { document ->
                    document.toObject(User::class.java)
                }
                _clients.value = clientList
            }
            .addOnFailureListener {
                _clients.value = emptyList() // En caso de error, lista vacía
            }
    }

}