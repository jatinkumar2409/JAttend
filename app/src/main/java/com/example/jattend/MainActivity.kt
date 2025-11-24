package com.example.jattend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jattend.Screens.DetailsScreen
import com.example.jattend.Screens.HomeScreen
import com.example.jattend.Screens.ScreenComposables.DetailsScreen.DetailsScreenViewModel
import com.example.jattend.Screens.ScreenComposables.HomeScreen.HomeScreen
import com.example.jattend.Screens.ScreenComposables.HomeScreen.HomeScreenViewModel
import com.example.jattend.Screens.ScreenComposables.SignInScreen.SignInViewModel
import com.example.jattend.Screens.SignInScreen
import com.example.jattend.ui.theme.JAttendTheme
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel by viewModel<HomeScreenViewModel>()
        val signInViewModel by viewModel<SignInViewModel>()
        val detailsScreenViewModel by viewModel<DetailsScreenViewModel>()
        enableEdgeToEdge()
        setContent {
            JAttendTheme {
                val navController = rememberNavController()
                val user = FirebaseAuth.getInstance().currentUser
                NavHost(
                    navController = navController ,
                    startDestination = if(user == null) SignInScreen else HomeScreen

                ){
                    composable<HomeScreen> {
                        HomeScreen(
                            navController = navController,
                            viewModel = homeScreenViewModel
                        )
                    }
                    composable<SignInScreen> {
                        com.example.jattend.Screens.ScreenComposables.SignInScreen.SignInScreen(
                            navController = navController ,
                            viewModel = signInViewModel
                        )
                    }
                    composable <DetailsScreen> {
                        val args = it.toRoute<DetailsScreen>()
                 com.example.jattend.Screens.ScreenComposables.DetailsScreen.DetailsScreen(navController = navController , viewModel = detailsScreenViewModel , id = args.id)
                    }
                }
            }
        }
    }
}
