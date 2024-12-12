package com.ud.sheltermind.views.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
        private val db = FirebaseFirestore.getInstance()
        private val auth = FirebaseAuth.getInstance()

        private val _userData = MutableStateFlow(User())
        val userData: StateFlow<User> = _userData

        private val _errorMessage = MutableStateFlow<String?>(null)
        val errorMessage: StateFlow<String?> = _errorMessage

        init {
            fetchUserProfile()
        }

        fun fetchUserProfile() {
            val currentUser = auth.currentUser
            currentUser?.let { user ->
                db.collection("users").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        val userData = document.toObject(User::class.java)
                        if (userData != null) {
                            _userData.value = userData
                        }
                    }
                    .addOnFailureListener { ex ->
                        _errorMessage.value = ex.message
                    }
            }
        }

        fun updateUserField(field: String, value: Any) {
            _userData.value = when (field) {
                "username" -> _userData.value.copy(name = value as String)
                "email" -> _userData.value.copy(email = value as String)
                "password" -> _userData.value.copy(password = value as String)
                "number" -> _userData.value.copy(number = value as String)
                "notificationsEnabled" -> _userData.value.copy(notifications = value as Boolean)
                "userType" -> _userData.value.copy(type = value as String)
                else -> _userData.value
            }
        }

        fun saveUserData(onSuccess: @Composable () -> Unit) {
            val currentUser = auth.currentUser
            currentUser?.let { user ->
                db.collection("users").document(user.uid).set(_userData.value)
                    .addOnSuccessListener { onSuccess }
                    .addOnFailureListener { ex ->
                        _errorMessage.value = ex.message
                    }
            }
        }
    }

