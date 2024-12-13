package com.ud.sheltermind.views.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ud.sheltermind.logic.dataclass.Target
import com.ud.sheltermind.logic.dataclass.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SyntomViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _states = MutableStateFlow<List<State>>(emptyList())
    val states: StateFlow<List<State>> = _states

    private val _targets = MutableStateFlow<List<Target>>(emptyList())
    val targets: StateFlow<List<Target>> = _targets

    fun consultStates(){
        val stateRef = db.collection("state")
        stateRef.get()
            .addOnSuccessListener { result ->
                val stateList = result.map { document ->
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val icon = document.getString("icon") ?: ""
                    val color = document.getString("color") ?: ""
                    State(id = id, name = name, icon = icon, color = color)
                }
                _states.value = stateList
            }
            .addOnFailureListener { exception ->
                // Manejo de error
                Log.d("FirestoreError", "Error al obtener los estados: ", exception)
            }
    }

    fun consultTargets(id: String) {
        val stateRef = db.collection("state").document(id)
        stateRef.get()
            .addOnSuccessListener { result ->
                val targets = result.get("targets") as? List<Map<String, Any>> ?: emptyList()
                Log.d("FirestoreError", targets.toString())
                // Mapea los datos de targets a una lista de objetos Target
                val targetList = targets.map { target ->
                    val name = target["name"] as? String ?: ""
                    val value = target["value"] as? Int ?: 0
                    Target(name = name, value = value)
                }
                _targets.value = targetList
            }
            .addOnFailureListener { exception ->
                // Manejo de error
                Log.d("FirestoreError", "Error al obtener los targets: ", exception)
            }
    }
}
