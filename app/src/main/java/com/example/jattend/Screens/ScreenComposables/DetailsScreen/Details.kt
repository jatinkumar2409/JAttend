package com.example.jattend.Screens.ScreenComposables.DetailsScreen

import android.widget.Button
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

import androidx.navigation.NavHostController
import com.example.jattend.R
import com.example.jattend.Screens.HomeScreen
import com.example.jattend.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController : NavHostController , viewModel: DetailsScreenViewModel , id : String) {
    LaunchedEffect(Unit) {
        viewModel.getSubject(id)
    }
    var check by remember() {
        mutableStateOf(false)
    }
    var keyboard = LocalSoftwareKeyboardController.current
    val subject by viewModel.subject
    Scaffold(
        modifier = Modifier.fillMaxSize() , topBar =  {
            TopAppBar(
                title = {
                    Text(text = "JAttend")
                }  ,
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {

                        Icon(imageVector = Icons.Default.KeyboardArrowLeft , contentDescription = null)
                    }
                }, actions = {
                    IconButton(onClick = {
                        check = !check
                    }) {
                        Icon(imageVector = Icons.Default.Edit , contentDescription = null)
                    }
                }
                , colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.White, containerColor = Purple40 , navigationIconContentColor = Color.White , actionIconContentColor = Color.White)
            )
        }
    ){
        ip ->
        if(subject.name.isEmpty() || subject.id != id){
            Box(modifier = Modifier.fillMaxSize().padding(ip) , contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        else {
            Column(
                modifier = Modifier.fillMaxSize().padding(ip),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.size(120.dp).clip(shape = CircleShape).background(viewModel.color)) {
                    Icon(
                        painter = painterResource(R.drawable.book_2_24px),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().padding(24.dp)
                    )
                }
                var name by remember {
                    mutableStateOf(subject.name)
                }
                var present by remember {
                    mutableStateOf(subject.present.toString())
                }
                var total by remember {
                    mutableStateOf(subject.total.toString())
                }
                TextField(value = name, onValueChange = {
                    name = it
                }, maxLines = 1, readOnly = !check, colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent ,
                    unfocusedContainerColor = Color.Transparent ,
                    focusedContainerColor = Color.Transparent
                ) , modifier = Modifier.fillMaxWidth(0.6f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total Days  ")
                    TextField(
                        value = total,
                        onValueChange = {
                            total = it
                        },
                        maxLines = 1,
                        readOnly = !check,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent ,
                            unfocusedContainerColor = Color.Transparent ,
                            focusedContainerColor = Color.Transparent
                        ))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Present Days")
                    TextField(
                        value = present,
                        onValueChange = {
                            present = it
                        },
                        maxLines = 1,
                        readOnly = !check,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent ,
                            unfocusedContainerColor = Color.Transparent ,
                            focusedContainerColor = Color.Transparent
                        ))
                }
                if (check) {
                    androidx.compose.material3.Button(onClick = {
                        if (name.trim()
                                .isNotEmpty() && total.isDigitsOnly() && present.isDigitsOnly()
                        ) {
                            viewModel.editSubject(
                                subject.copy(
                                    name = name,
                                    total = total.toInt(),
                                    present = present.toInt()
                                )
                            )
                        }
                        check = false
                        keyboard?.hide()
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = viewModel.color,
                            contentColor = Color.White
                        )) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }

}