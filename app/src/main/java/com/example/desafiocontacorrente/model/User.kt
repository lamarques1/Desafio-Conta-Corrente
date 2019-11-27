package com.example.desafiocontacorrente.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email : String,
    @SerializedName("profile") val profile: String,
    @SerializedName("balance") val balance: String
)