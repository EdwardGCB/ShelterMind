package com.ud.sheltermind.views.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ud.sheltermind.logic.dataclass.Client
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DiscoverViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Estado para el tipo de usuario
    private val _userType = MutableStateFlow("Cliente")
    val userType: StateFlow<String> = _userType

    // Estado para los clientes
    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients

    // Estado para los resultados de búsqueda
    private val _filteredClients = MutableStateFlow<List<Client>>(emptyList())
    val filteredClients: StateFlow<List<Client>> = _filteredClients

    init {
        fetchUserType()
        fetchClients() // Recuperar los clientes al inicializar el ViewModel
    }

    // Obtener el tipo de usuario logueado
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

    // Obtener los clientes de la base de datos (solo de tipo Cliente)
    private fun fetchClients() {
        db.collection("users")
            .whereEqualTo("type", "Cliente") // Filtrar solo clientes
            .get()
            .addOnSuccessListener { result ->
                val clientsList = result.mapNotNull { document ->
                    val user = document.toObject(User::class.java)
                    Client(
                        user = user,
                        syntomValue = user.syntomValue,
                        lastQuestion = user.lastQuestion
                    )
                }
                _clients.value = clientsList
                _filteredClients.value = clientsList // Inicializar con la lista completa
            }
            .addOnFailureListener {
                _clients.value = emptyList()
                _filteredClients.value = emptyList()
            }
    }

    // Función para filtrar clientes por nombre o syntomValue
    fun searchClients(query: String) {
        val currentClients = _clients.value
        val filtered = currentClients.filter { client ->
            client.user.name.contains(query, ignoreCase = true) ||
                    client.syntomValue.toString().contains(query)
        }
        _filteredClients.value = filtered
    }
}

