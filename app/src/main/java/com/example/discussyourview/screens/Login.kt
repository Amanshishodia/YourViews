package com.example.discussyourview.screens


import android.content.*
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.discussyourview.navigation.Routes
import com.example.discussyourview.viewmodel.AuthViewModel


@Composable
fun Login(navController: NavHostController) {
    val context= LocalContext.current

    val authViewModel:AuthViewModel= viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(firebaseUser) {
        if(firebaseUser!=null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }

        }
    }

    LaunchedEffect(error) {
        if(firebaseUser!=null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }

        }
    }
    error?.let {

      Toast.makeText(context,"Some error Occured, Please Enter Correct Credentials ",Toast.LENGTH_SHORT).show()

    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(text = "Login", style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp))
        Box(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },  // Make sure this refers to androidx.compose.material3.Text
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Enter your Password") },  // Make sure this refers to androidx.compose.material3.Text
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(30.dp))

        ElevatedButton(onClick = {

            if (email.isEmpty()||password.isEmpty()){
               Toast.makeText(context,"Please fill all the Details",Toast.LENGTH_SHORT).show()
            }else{
                authViewModel.login(email,password,context )
            }


        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Login Here",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
        Box(modifier = Modifier.height(30.dp))

        TextButton(onClick = {
            navController.navigate(Routes.Register.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }, modifier = Modifier.fillMaxWidth()) {

            Text(text = "New user SignUp Here", style = TextStyle(fontSize = 16.sp))

        }


    }
}

@Preview(showBackground = true)
@Composable
fun LoginView() {
  //  Login()
}
