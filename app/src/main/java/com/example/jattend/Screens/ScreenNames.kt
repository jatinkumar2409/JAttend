package com.example.jattend.Screens

import kotlinx.serialization.Serializable

@Serializable
object SignInScreen

@Serializable
object HomeScreen

@Serializable
data class DetailsScreen(val id : String)
