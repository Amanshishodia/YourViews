package com.example.discussyourview.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.discussyourview.R
import com.example.discussyourview.navigation.Routes
import com.example.discussyourview.viewmodel.AuthViewModel


@Composable
fun Register(navController: NavHostController) {
    val authViewModel:AuthViewModel= viewModel()
    val firebaseUser  by authViewModel.firebaseUser.observeAsState(null)
    var name by remember {
        mutableStateOf("")
    }
    var bio by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)

    }
    val context= LocalContext.current

    var launcher= rememberLauncherForActivityResult(contract =ActivityResultContracts.GetContent() ) {
        uri:Uri?->
        imageUri=uri
    }
    var permissionlauncher= rememberLauncherForActivityResult(contract =ActivityResultContracts.RequestPermission() ){
        isGranted:Boolean->
        if(isGranted){

        }else{

        }
    }
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }



    LaunchedEffect(firebaseUser) {
        if(firebaseUser!=null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }

        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            text = "Sign Up",
            style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
        )



        Image(painter = if(imageUri==null)painterResource(id = R.drawable.person)
        else rememberAsyncImagePainter(model = imageUri)

            , contentDescription = "person",modifier= Modifier
                .size(96.dp)
                .clip(
                    CircleShape
                )
                .background(Color.Gray)
                .clickable {

                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionlauncher.launch(permissionToRequest)
                    }

                }, contentScale = ContentScale.Crop)
        Box(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Enter Your Name") },  // Make sure this refers to androidx.compose.material3.Text
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text(text = "Enter Your Name") },  // Make sure this refers to androidx.compose.material3.Text
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text(text = "Enter the Bio") },  // Make sure this refers to androidx.compose.material3.Text
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(20.dp))

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
            if(name.isEmpty()||email.isEmpty()||bio.isEmpty()||userName.isEmpty()||password.isEmpty()||imageUri==null){
                                     Toast.makeText(context,"Please fill all the Details",Toast.LENGTH_SHORT).show()
            }else{
                authViewModel.register(email, password ,name,bio,userName, imageUri!!,context)

                                 }


        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sign Up ",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
        Box(modifier = Modifier.height(30.dp))

        TextButton(onClick = { navController.navigate(Routes.Login.routes){
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop=true
        } }, modifier = Modifier.fillMaxWidth()) {

            Text(text = "Account Already Exist Click Here", style = TextStyle(fontSize = 16.sp))

        }


    }


}

@Preview(showBackground = true)
@Composable
fun RegisterView() {
  //  Register()
}