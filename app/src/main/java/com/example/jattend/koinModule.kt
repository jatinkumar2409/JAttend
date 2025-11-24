package com.example.jattend

import androidx.lifecycle.ViewModel
import com.example.jattend.Screens.ScreenComposables.DetailsScreen.DetailsScreenViewModel
import com.example.jattend.Screens.ScreenComposables.HomeScreen.HomeScreenViewModel
import com.example.jattend.Screens.ScreenComposables.SignInScreen.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel {
        SignInViewModel()

    }
    viewModel{
        HomeScreenViewModel()

    }
    viewModel {
        DetailsScreenViewModel()
    }
}