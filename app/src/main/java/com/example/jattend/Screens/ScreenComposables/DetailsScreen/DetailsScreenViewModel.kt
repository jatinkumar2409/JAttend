package com.example.jattend.Screens.ScreenComposables.DetailsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.jattend.ui.theme.Purple40
import com.example.jattend.utils.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore.*

class DetailsScreenViewModel : ViewModel() {
    val colors = listOf(Color.Red, Purple40, Color.Magenta, Color.Blue)
    val color = colors.random()
    val subject = mutableStateOf(Subject())
    val auth = FirebaseAuth.getInstance()
    val firestore = getInstance()

    fun getSubject(id: String) {
        firestore.collection("users").whereEqualTo("email", auth.currentUser?.email).get()
            .addOnSuccessListener { it ->
                for (i in it) {
                    i.reference.collection("subjects").document(id).get()
                        .addOnSuccessListener { it ->
                            val s = it.toObject(Subject::class.java)
                            subject.value = s ?: Subject()
                        }
                }
            }
    }

    fun editSubject(mysubject: Subject) {
        firestore.collection("users").whereEqualTo("email", auth.currentUser?.email).get()
            .addOnSuccessListener { it ->
                for (i in it) {
                    i.reference.collection("subjects").document(mysubject.id).set(mysubject)
                        .addOnSuccessListener { it ->
                            subject.value = mysubject
                        }
                }
            }
    }
}