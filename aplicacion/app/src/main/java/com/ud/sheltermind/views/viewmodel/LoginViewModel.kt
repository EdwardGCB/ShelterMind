package com.ud.sheltermind.views.viewmodel

import com.google.firebase.auth.FirebaseAuth

class LoginViewModel {

    fun login(username: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword()
    }
}