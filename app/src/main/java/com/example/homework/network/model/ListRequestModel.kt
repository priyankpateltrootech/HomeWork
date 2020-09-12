package com.example.homework.network.model

import com.google.gson.annotations.JsonAdapter

data class ListRequestModel(

    @JsonAdapter(EncryptRequestFactory.NoEncryptInteger::class)
    val page: Int,

    @JsonAdapter(EncryptRequestFactory.NoEncryptInteger::class)
    val perPage: Int
)