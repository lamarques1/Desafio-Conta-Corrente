package com.example.desafiocontacorrente.model

import com.google.gson.annotations.SerializedName

data class Banking (
    @SerializedName("id") val id : Int,
    @SerializedName("id_from") val id_from : Int,
    @SerializedName("id_to") val id_to : Int,
    @SerializedName("value") val value : Int,
    @SerializedName("data") val data : String
)