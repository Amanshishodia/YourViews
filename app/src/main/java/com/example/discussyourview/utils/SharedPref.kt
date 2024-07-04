package com.example.discussyourview.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.ProvidableCompositionLocal

object SharedPref {

    fun storeData(fullName: String, email: String, bio: String, userName: String, imageUrl: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", fullName)
        editor.putString("email", email)
        editor.putString("bio", bio)
        editor.putString("userName", userName)
        editor.putString("imageUrl", imageUrl)
        editor.apply()
    }

    fun getUserName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("userName", "")!!
    }

    fun getName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("name", "")!!
    }

    fun getBio(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("bio", "")!!
    }

    fun getEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email", "")!!
    }

    fun getImageUrl(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl", "")!!
    }
}