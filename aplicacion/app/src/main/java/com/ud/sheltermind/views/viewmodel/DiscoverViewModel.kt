package com.ud.sheltermind.views.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ud.sheltermind.logic.dataclass.Client
import com.ud.sheltermind.logic.dataclass.Note
import com.ud.sheltermind.logic.dataclass.Opinion
import com.ud.sheltermind.logic.dataclass.Psychologist
import com.ud.sheltermind.logic.dataclass.State
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
    private val _clientsList = MutableStateFlow<List<Client>>(emptyList())
    val clientsList: StateFlow<List<Client>> = _clientsList

    private val _psychologistList = MutableStateFlow<List<Psychologist>>(emptyList())
    val psychologistList: StateFlow<List<Psychologist>> = _psychologistList

    // Estado para los resultados de búsqueda
    private val _filteredClients = MutableStateFlow<List<Client>>(emptyList())
    val filteredClients: StateFlow<List<Client>> = _filteredClients

    init {
        fetchUserType()
        fetchClientsFromFirestore() // Recuperar los clientes al inicializar el ViewModel
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

    // Filtrar los usuarios por tipo "Client"
    private fun fetchClientsFromFirestore() {
        db.collection("users")
            .whereEqualTo("type", "Cliente") // Filtra los usuarios con el tipo "Cliente"
            .get()
            .addOnSuccessListener { querySnapshot ->
                val clients = querySnapshot.documents.mapNotNull { document ->
                    // Convertir cada documento a un objeto Client
                    val client = Client(
                        user = User(
                            id = document.getString("id") ?: "",
                            name = document.getString("name") ?: "",
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
                    client
                }
                // Asignar los datos de los clientes a la variable o flujo correspondiente
                _clientsList.value = clients
                Log.d("Firestore", "Clients fetched: $clients")
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al cargar los clientes: ${ex.message}"
                Log.e("Firestore", "Error fetching clients: ${ex.message}")
            }
    }

    // Filtrar los usuarios por tipo "Psychologist"
    fun fetchPsychologistsFromFirestore() {
        db.collection("users")
            .whereEqualTo("type", "Psicologo") // Filtra los usuarios con el tipo "Psicologo"
            .get()
            .addOnSuccessListener { querySnapshot ->
                val psychologists = querySnapshot.documents.mapNotNull { document ->
                    // Convertir cada documento a un objeto Psychologist
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
                    psychologist
                }
                // Asignar los datos de los psicólogos a la variable o flujo correspondiente
                _psychologistList.value = psychologists
                Log.d("Firestore", "Psychologists fetched: $psychologists")
            }
            .addOnFailureListener { ex ->
                _errorMessage.value = "Error al cargar los psicólogos: ${ex.message}"
                Log.e("Firestore", "Error fetching psychologists: ${ex.message}")
            }
    }

    // Función para filtrar clientes por nombre o syntomValue
    fun searchClients(query: String) {
        val currentClients = _clientsList.value
        val filtered = currentClients.filter { client ->
            client.user!!.name.contains(query, ignoreCase = true) ||
                    client.syntomValue.toString().contains(query)
        }
        _filteredClients.value = filtered
    }
}

