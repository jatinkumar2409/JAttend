package com.example.jattend.Screens.ScreenComposables.SignInScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jattend.utils.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignInViewModel : ViewModel(){
    val auth = FirebaseAuth.getInstance()
    val firstore = FirebaseFirestore.getInstance()
    var authStatus by mutableStateOf(false)
    fun signIn(email : String , password : String){
        auth.createUserWithEmailAndPassword(email , password).addOnSuccessListener{
          authStatus = true
            firstore.collection("users").add(User(email ,password))
        }
    }

    fun logIn(email : String , password : String){
        auth.signInWithEmailAndPassword(email , password).addOnSuccessListener{
          authStatus = true
        }
    }
    fun forgetPassword(email: String){
        auth.sendPasswordResetEmail(email)
    }
}