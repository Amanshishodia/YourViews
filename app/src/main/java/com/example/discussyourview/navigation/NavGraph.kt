package com.example.discussyourview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.webkit.Profile
import com.example.discussyourview.screens.AddThreads
import com.example.discussyourview.screens.BottomNav
import com.example.discussyourview.screens.Home
import com.example.discussyourview.screens.Login
import com.example.discussyourview.screens.Notification
import com.example.discussyourview.screens.Profile
import com.example.discussyourview.screens.Register
import com.example.discussyourview.screens.Search
import com.example.discussyourview.screens.Splash


@Composable
fun NavGraph(navController: NavHostController){
           NavHost(navController = navController, startDestination = Routes.Splash.routes) {

               composable(Routes.Splash.routes){
                   Splash(navController)
               }
               composable(Routes.Home.routes){
                   Home()
               }
               composable(Routes.Notification.routes){
                   Notification()
               }
               composable(Routes.Search.routes){
                   Search()

               }
               composable(Routes.Profile.routes){
                   Profile(navController)
               }
               composable(Routes.AddThread.routes){
                   AddThreads(navController)
               }
               composable(Routes.BottomNav.routes){
                   BottomNav(navController)
               }
               composable(Routes.Login.routes){
                   Login(navController)
               }
               composable(Routes.Register.routes){
                   Register(navController)
               }





           }
}