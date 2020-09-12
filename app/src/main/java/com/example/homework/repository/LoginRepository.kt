package com.example.homework.repository

import android.app.Application
import android.util.Log
import com.example.homework.base.MediatorRepository
import com.example.homework.data.model.UserModel
import com.example.homework.mock.objects.PersonModel
import com.example.homework.network.webservices.ApiClient
import com.example.homework.network.webservices.ApiInterface
import com.example.homework.network.webservices.RetrofitCallbackHandler
import com.example.homework.model.LoginModel

class LoginRepository(val application: Application) : MediatorRepository<LoginModel, PersonModel>(
    RetrofitCallbackHandler(application, "UserModel")
) {

    /**
     * Here we place server call.
     */
    override fun loadFromWebServices(request: LoginModel) {

        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)

        val responseCall = apiInterface.getUserLogin(request)

        // TODO: set start loading to true if you want loader immediately when server call is placed
        loadFromWebServices(responseCall, true)
    }
}