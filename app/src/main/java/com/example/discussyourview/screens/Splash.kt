package com.example.discussyourview.screens

import androidx.compose.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.discussyourview.R
import com.example.discussyourview.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay



@Composable
fun Splash(navController: NavHostController){

ConstraintLayout(modifier=Modifier.fillMaxSize()){
    val (image)=createRefs()

    Image(painter = painterResource(id = R.drawable.logo), contentDescription ="logo" , modifier = Modifier.constrainAs(image){
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    })
}
    LaunchedEffect(true) {
        delay(3000)
        if (FirebaseAuth.getInstance().currentUser!=null){
            navController.navigate(Routes.BottomNav.routes)

        }

        else{
            navController.navigate(Routes.Login.routes)
        }
    }
}