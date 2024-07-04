package com.example.discussyourview.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.discussyourview.R

@Composable
fun AddThreads(){
ConstraintLayout (modifier = Modifier
    .fillMaxSize()
    .padding(16.dp)){
    val (crossPic,text,logo,editText,attachMedia,replyText,button,imageBox)=createRefs()
    Image(painter = painterResource(id = R.drawable.baseline_close_24), contentDescription = "close")

}
}


@Preview(showBackground = true)
@Composable
fun AddPostView(){

}