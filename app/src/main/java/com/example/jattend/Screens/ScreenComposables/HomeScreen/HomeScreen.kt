package com.example.jattend.Screens.ScreenComposables.HomeScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jattend.R
import com.example.jattend.Screens.DetailsScreen
import com.example.jattend.Screens.HomeScreen
import com.example.jattend.ui.theme.Purple40
import com.example.jattend.utils.Subject


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(navController: NavController = rememberNavController() , viewModel: HomeScreenViewModel){
    val subjects = viewModel._subjects
    var isAdd by remember {
        mutableStateOf(false)
    }
    var subject by remember {
        mutableStateOf(Subject("" , "" ,0 , 0))
    }
    var name by remember {
        mutableStateOf("")
    }
    var total by remember {
        mutableStateOf("")
    }
    var present by remember{
        mutableStateOf("")
    }
    val context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize() , topBar = {
        TopAppBar(
            title = {
                Text(text = "JAttend")
            } , colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.White, containerColor = Purple40)
        )
    } , floatingActionButton = {
        Button(onClick = {
            if(!isAdd) isAdd = true
        } , shape = CircleShape) {
            Icon(imageVector = Icons.Default.Add ,  contentDescription = "AddSubject" , modifier = Modifier.padding(horizontal = 2.dp , vertical = 8.dp))
        }
    }) { ip ->
        Log.d("tag" , subjects.toString())
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(ip)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(subjects){ subject ->
         ShowSubject(
             subject = subject , onSubjectClick = { it ->
                 navController.navigate(DetailsScreen(it))
             } , onPresent = { it , present , total ->
                      viewModel.addPresent(it , present+1 , total +1)
             } , onTotal = { it , total ->
                  viewModel.addTotal(it , total+1)
             }
         )
                }
                item {
                    if(isAdd){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {
                            OutlinedTextField(value = name , onValueChange = {
                                name = it
                            } ,maxLines = 1 , placeholder = {Text(text = "Subject")} , modifier = Modifier.weight(2f))
                            Spacer(modifier = Modifier.width(2.dp))
                            OutlinedTextField(value = total , onValueChange = {
                              total = it
                            }  ,maxLines = 1, placeholder = {Text(text = "T")} ,  modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(2.dp))
                            OutlinedTextField(value = present , onValueChange = {
                                 present = it
                            }, maxLines = 1 , placeholder = {Text(text = "P")} ,  modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(2.dp))
                            Button(onClick = {
                              if(name.trim().isNotEmpty() && total.isDigitsOnly() && present.isDigitsOnly()){
                                  subject = Subject(name = name , total = total.toInt() , present = present.toInt())
                                  viewModel.addSubject(subject)
                              }
                                else{
                                  Toast.makeText(context, "Invalid details", Toast.LENGTH_SHORT).show()
                              }
                                isAdd = false
                            }) {
                                Icon(imageVector = Icons.Default.Check , contentDescription = null , modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ShowSubject(subject : Subject = Subject("" , "Marketing Management" , 74 , 46) , onSubjectClick : (String) -> Unit = {}  , onPresent : (String , Int ,Int) ->Unit , onTotal : (String , Int) -> Unit ){
    val colors = listOf(Color.Cyan , Purple40 , Color.Magenta , Color.Gray , Color.Green)
    val color = colors.random()
    val per = String.format ( "%.2f",(subject.present + 0.0)/subject.total * 100)
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSubjectClick(subject.id) }, verticalAlignment = Alignment.CenterVertically ,
         horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween) {
        Box(modifier = Modifier.background(color)){
            Icon(painter = painterResource(R.drawable.book_2_24px) , contentDescription = "book", modifier = Modifier
                .padding(12.dp)
                .size(32.dp) , tint = if (isSystemInDarkTheme()) Color.DarkGray else Color.White)
        }
        Text(text = subject.name , modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(6.dp) , maxLines = 2 , overflow = TextOverflow.Clip)
        Box(modifier = Modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(6.dp)){
            Text(text = "$per%", modifier = Modifier.padding(6.dp), color = Color.White)
        }
        IconButton(onClick = {
            onPresent(subject.id ,subject.present , subject.total)
                       Log.d("tag" , "i am clicked")      } , colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Green , contentColor = Color.White)) {
            Icon(painter = painterResource(R.drawable.ink_pen_24px), contentDescription = "present")
        }
        IconButton(onClick = {onTotal(subject.id , subject.total)} ,  colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Red , contentColor = Color.White)) {
            Icon(imageVector = Icons.Default.Clear , contentDescription = "absent")
        }
    }
}