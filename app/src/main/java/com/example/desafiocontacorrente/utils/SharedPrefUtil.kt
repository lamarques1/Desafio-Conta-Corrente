package com.example.desafiocontacorrente.utils

import android.content.Context
import com.example.desafiocontacorrente.R
import com.example.desafiocontacorrente.model.User
import com.google.gson.Gson

open class SharedPrefUtil {
    fun setUser(context: Context?, user: User){
        val settings = context?.getSharedPreferences(context.getString(R.string.shared_pref_file_key), 0)
        val editor = settings?.edit()

        val gson = Gson()
        val userJson = gson.toJson(user)

        editor?.putString("user", userJson)
        editor?.apply()
    }

    fun getUser(context: Context?): User{
        val settings = context?.getSharedPreferences(context.getString(R.string.shared_pref_file_key), 0)
        val gson = Gson()

        val userJson = settings?.getString("user", "")
        val user = gson.fromJson<User>(userJson, User::class.java)

        return user
    }


}