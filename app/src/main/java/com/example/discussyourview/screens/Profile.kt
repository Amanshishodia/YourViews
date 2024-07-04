package com.example.discussyourview.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.discussyourview.navigation.Routes
import com.example.discussyourview.viewmodel.AuthViewModel


@Composable
fun Profile(navHostController: NavHostController){
    
    val authViewModel:AuthViewModel= viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser) {
        if(firebaseUser==null){
            navHostController.navigate(Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop=true
            }

        }
    }
    
   TextButton(onClick = { authViewModel.logOut() }, modifier = Modifier.fillMaxWidth()) {

       Text(text = "You are here in the Profile page click for the logout", style = TextStyle(fontSize = 16.sp))

   }
       

}