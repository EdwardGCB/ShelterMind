package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ud.sheltermind.logic.dataclass.Client

class UserAccountViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _cliente = MutableLiveData<Client?>()
    val cliente: LiveData<Client?> = _cliente

    fun cargarCliente(clientId: String) {
        db.collection("clientes").document(clientId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cliente = document.toObject(Client::class.java)
                    _cliente.value = cliente
                } else {
                    _cliente.value = null
                }
            }
            .addOnFailureListener {
                _cliente.value = null
            }
    }
}
