package com.example.discussyourview.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.discussyourview.model.UserModel
import com.example.discussyourview.utils.SharedPref
import com.google.firebase.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID


class AuthViewModel : ViewModel() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: MutableLiveData<FirebaseUser?> get() = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> get() = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context:Context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {


                _firebaseUser.postValue(auth.currentUser)
                getData(auth.currentUser!!.uid,context)
            } else {
                _error.postValue("Something went wrong: ${task.exception?.message}")
            }
        }
    }

    private fun getData(uid: String, context:Context) {




        userRef.child(uid).addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val userData=snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(userData!!.fullName, userData!!.email, userData!!.bio, userData!!.userName, userData!!.profileImageUrl,
                    context)

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun register(
        email: String,
        password: String,
        bio: String,
        userName: String,
        fullName: String,
        imageUri: Uri,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                saveImage(email, password, bio, userName, fullName, imageUri, auth.currentUser?.uid, context)
            } else {
                _error.postValue("Something went wrong: ${task.exception?.message}")
            }
        }
    }

    private fun saveImage(
        email: String,
        password: String,
        bio: String,
        userName: String,
        fullName: String,
        imageUri: Uri,
        uid: String?,
        context: Context
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                saveData(email, password, fullName, bio, userName, uri.toString(), uid, context)
            }.addOnFailureListener { exception ->
                _error.postValue("Failed to get download URL: ${exception.message}")
            }
        }.addOnFailureListener { exception ->
            _error.postValue("Image upload failed: ${exception.message}")
        }
    }

    private fun saveData(
        email: String,
        password: String,
        fullName: String,
        bio: String,
        userName: String,
        profileImageUrl: String,
        uid: String?,
        context: Context
    ) {
        uid?.let {
            val firestoreDb=Firebase.firestore
            val followerRefs=firestoreDb.collection("followers").document(uid!!)
            val followingRefs=firestoreDb.collection("following").document(uid!!)

            followingRefs.set(mapOf("followingIds" to listOf<String>()))
            followerRefs.set(mapOf("followerIds" to listOf<String>()))


            val userData = UserModel(email, password, fullName, bio, userName, profileImageUrl, it)
            userRef.child(it).setValue(userData).addOnSuccessListener {
                SharedPref.storeData(fullName, email, bio, userName, profileImageUrl, context)
            }.addOnFailureListener { exception ->
                _error.postValue("Failed to save user data: ${exception.message}")
            }
        } ?: run {
            _error.postValue("User ID is null")
        }
    }


      fun logOut(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }
}