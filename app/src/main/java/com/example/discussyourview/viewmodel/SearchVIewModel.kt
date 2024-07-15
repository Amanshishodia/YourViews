package com.example.discussyourview.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.discussyourview.model.ThreadModel
import com.example.discussyourview.model.UserModel
import com.example.discussyourview.utils.SharedPref
import com.google.firebase.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import java.util.UUID


class SearchVIewModel : ViewModel() {


    private val db = FirebaseDatabase.getInstance()
    val users = db.getReference("users")


private var _Users = MutableLiveData<List<UserModel>>()


    var userList:LiveData<List<UserModel>> =_Users

init {
    fetchUsers {
        _Users.value=it
    }
}
    private fun fetchUsers(onResult: (List<UserModel>)->Unit){
        users.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result= mutableListOf<UserModel>()
                for (threadSnapshot in snapshot.children){
                 val thread=threadSnapshot.getValue(UserModel::class.java)
                   result.add(thread!!)
                }

                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    fun fetchUserFromThread(thread: ThreadModel,onResult: (UserModel)->Unit){
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user=snapshot.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


    }



