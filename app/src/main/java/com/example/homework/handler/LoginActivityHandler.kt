package com.example.homework.handler

import android.app.Application
import com.example.homework.viewmodel.LoginViewModel
import com.example.homework.data.model.UserModel

class LoginActivityHandler(val application: Application) {

    fun validateAndLogin(viewModel: LoginViewModel) {



            viewModel.validateAndLogin()

    }

    fun retrieveUserModel(viewModel: LoginViewModel) {

        val userModel = UserModel.retrieve(application)


    }

    fun logout(viewModel: LoginViewModel) {

        UserModel.logout(application)

        viewModel.isLoggedIn.value = false
    }
}