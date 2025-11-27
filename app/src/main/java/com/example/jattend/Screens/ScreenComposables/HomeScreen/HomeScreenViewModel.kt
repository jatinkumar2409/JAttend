package com.example.jattend.Screens.ScreenComposables.HomeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jattend.ui.theme.Purple40
import com.example.jattend.utils.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    val _subjects = mutableStateListOf<Subject>()
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    init {
        getSubjects()
    }
    fun addSubject(subject : Subject){
        firestore.collection("users").whereEqualTo("email" , auth.currentUser?.email).get().addOnSuccessListener { it ->
            for (i in it){
                val id = i.reference.collection("subjects").document().id
                 subject.id = id
                 i.reference.collection("subjects").document(id).set(subject).addOnSuccessListener { it ->
                   _subjects.add(subject)
                 }
            }
        }
    }
    fun getSubjects(){
        firestore.collection("users").whereEqualTo("email" , auth.currentUser?.email).get().addOnSuccessListener { it ->
            for (i in it){
                i.reference.collection("subjects").get().addOnSuccessListener { list ->
                     val subjects = list.toObjects(Subject::class.java)
                    _subjects.addAll(subjects)
                }
            }
        }
    }
    fun addPresent(id : String , present : Int , total: Int){
        viewModelScope.launch {
            Log.d("tag1" , id.toString())
            firestore.collection("users").whereEqualTo("email", auth.currentUser?.email).get()
                .addOnSuccessListener { it ->
                    for (i in it) {
                        i.reference.collection("subjects").document(id).update("present", present)
                            .addOnSuccessListener { it ->
                                i.reference.collection("subjects").document(id)
                                    .update("total", total).addOnSuccessListener { it ->
                                    val index = _subjects.indexOfFirst { it.id == id }
                                    _subjects[index] =
                                        _subjects[index].copy(present = present, total = total)
                                }
                            }
                    }
                }
        }
    }
    fun addTotal(id : String , total : Int){
        firestore.collection("users").whereEqualTo("email" , auth.currentUser?.email).get().addOnSuccessListener { it ->
            for ( i in it){
                i.reference.collection("subjects").document(id).update("total" , total).addOnSuccessListener {
                    val index = _subjects.indexOfFirst { it.id == id }
                    _subjects[index] = _subjects[index].copy(total = total)
                }
            }
        }
    }
    fun deleteSubject(id : String){
        firestore.collection("users").whereEqualTo("email" , auth.currentUser?.email).get().addOnSuccessListener { it ->
            for ( i in it) {
             i.reference.collection("subjects").document(id).delete().addOnSuccessListener {
                 val index = _subjects.indexOfFirst { it.id == id }
                 _subjects.removeAt(index)
             }
            }
        }
            }
    }

