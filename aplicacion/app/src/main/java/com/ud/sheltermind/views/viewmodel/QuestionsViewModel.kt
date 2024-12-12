package com.ud.sheltermind.views.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ud.sheltermind.logic.dataclass.Answer
import com.ud.sheltermind.logic.dataclass.Question
import com.ud.sheltermind.logic.dataclass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuestionsViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _questionnaireComplete = MutableStateFlow(false)
    val questionnaireComplete: StateFlow<Boolean> = _questionnaireComplete

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    fun checkComplete(user: User){
        db.collection("questions").get()
            .addOnSuccessListener { documents ->
                val totalQuestions = documents.size()-1
                _questionnaireComplete.value = user.lastQuestion >= totalQuestions
            }
            .addOnFailureListener {
                _questionnaireComplete.value = false
            }
    }

    fun consultQuestion() {
        val questionsRef = db.collection("questions")
        questionsRef.get()
            .addOnSuccessListener { result ->
                val questionList = result.map { document ->
                    val id = document.getString("id") ?: ""
                    val number = document.getLong("number")?.toInt() ?: 0
                    val text = document.getString("text") ?: ""
                    val options = document.get("options") as? List<Map<String, Any>> ?: emptyList()
                    val answers = options.map { option ->
                        Answer(
                            name = option["name"] as? String ?: "",
                            value = (option["value"] as? Long)?.toInt() ?: 0
                        )
                    }
                    Question(id = id, number = number, text = text, options = answers)
                }
                _questions.value = questionList
            }
    }

}