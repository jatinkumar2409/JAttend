package com.example.jattend.Screens.ScreenComposables.SignInScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jattend.Screens.HomeScreen
import com.example.jattend.Screens.SignInScreen
import com.example.jattend.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(viewModel: SignInViewModel , navController: NavHostController){
    val authStatus = viewModel.authStatus
    var isLogIn by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize() , topBar = {
        TopAppBar(
            title = {
                Text(text = "JAttend")
            } , colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.White, containerColor = Purple40)
        )
    }) { ip ->
        if(authStatus) navController.navigate(HomeScreen){
            popUpTo(SignInScreen) {
                inclusive = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip)
        ){
            Text(
                text = if(!isLogIn) "Create Account" else "Welcome Back" ,
                fontSize = 32.sp ,
                modifier = Modifier.padding(horizontal = 8.dp , vertical = 12.dp)
            )
            Text(text = "Email" , fontSize = 18.sp , modifier = Modifier.padding(horizontal = 6.dp , vertical = 4.dp))
            OutlinedTextField(value = email , onValueChange = {
                email = it
            } , modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp))
            Text(text = "Password" , fontSize = 18.sp , modifier = Modifier.padding(horizontal = 6.dp , vertical = 4.dp))
            OutlinedTextField(value = password , onValueChange = {
                password = it
            } , modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    if(!isLogIn) {
                        if (email.trim().isNotEmpty() && password.trim()
                                .isNotEmpty()
                        ) viewModel.signIn(email, password)
                    }
                    else{
                        if(email.trim().isNotEmpty() && password.trim().isNotEmpty()) viewModel.logIn(email , password)
                    }
                }){
                    Text(text = if(!isLogIn) "SignUp" else "Login")
                }
                TextButton(onClick = {
                   if(!isLogIn) isLogIn = true
                    else{
                        if (email.isNotEmpty())  viewModel.forgetPassword(email)
                     else Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(if(!isLogIn) "Login" else "ForgetPassword?")
                }
            }
        }
    }
}