package com.example.desafiocontacorrente.model

import com.google.gson.annotations.SerializedName

data class Status (
    @SerializedName("status") val status: Boolean
)