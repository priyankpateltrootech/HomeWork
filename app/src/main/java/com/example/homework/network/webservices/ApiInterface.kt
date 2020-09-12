package com.example.homework.network.webservices

import com.example.homework.network.model.BaseResponse
import com.example.homework.data.model.UserModel
import com.example.homework.mock.objects.PersonModel
import com.example.homework.model.LoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Aaditya on 06/08/2019.
 */
interface ApiInterface {

    // TODO  --------------------------  YOUR COMPONENT NAME (e.g. LOGIN, HOME SCREEN)  --------------------------------

    @POST("login")
    fun getUserLogin(@Body request: LoginModel): Call<PersonModel>
}