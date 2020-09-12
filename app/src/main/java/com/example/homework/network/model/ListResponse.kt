package com.example.homework.network.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("list") val list: List<T>,

    @JsonAdapter(EncryptRequestFactory.NoEncryptInteger::class)
    @SerializedName("current_page") val currentPage: Int,

    @JsonAdapter(EncryptRequestFactory.NoEncryptInteger::class)
    @SerializedName("total_page") val totalPage: Int
)