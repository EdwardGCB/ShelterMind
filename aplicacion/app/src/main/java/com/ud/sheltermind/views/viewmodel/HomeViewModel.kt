package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private var syntomListener: ListenerRegistration? = null
    private val db = Firebase.firestore

    //private val _syntoms = MutableStateFlow<List<Syntom>>(emptyList())
    //val players: StateFlow<List<Syntom>> = _syntoms

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

}