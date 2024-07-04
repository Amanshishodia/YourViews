package com.example.discussyourview.model

import android.content.Context

data class UserModel(
    val email: String = "",
    val password: String = "",
    val fullName: String = "", // changed 'name' to 'fullName'
    val bio: String = "",
    val userName: String = "",
    val profileImageUrl: String = "",
    val uid: String = "",
    val context: Context? = null
)
