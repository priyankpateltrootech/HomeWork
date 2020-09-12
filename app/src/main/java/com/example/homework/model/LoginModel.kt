package com.example.homework.model


import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("email") var username: String,
    @SerializedName("password") var password: String

)