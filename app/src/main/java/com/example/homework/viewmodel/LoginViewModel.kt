package com.example.homework.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.homework.R
import com.example.homework.base.BaseApplication
import com.example.homework.model.LoginModel
import com.example.homework.repository.LoginRepository


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val loginModel = LoginModel(
        "",
        ""
    )

    private val loginRepository = LoginRepository(application)

    val errorEmail = MutableLiveData<String>()
    val errorPassword = MutableLiveData<String>()
    val responseLiveData = loginRepository.responseAsLiveData
    val servererror = loginRepository.serverError
    val isLoading = loginRepository.loadingLiveData
    val isLoggedIn = MutableLiveData<Boolean>()

    val majorError = MutableLiveData<String>()

    init {

        loginRepository.setMajorErrorMutableLiveData(majorError)
    }

    fun validateAndLogin() {

        if (loginModel.username.isEmpty()) {

            errorEmail.value = "Please enter username."

        }  else if (loginModel.password.isEmpty()) {
            errorEmail.value = ""
            errorPassword.value = "Please enter password."

        } else if (loginModel.password.length < 8) {

            errorPassword.value = "Password length can\\'t be less than 8 characters."

        } else if (loginModel.password.length > 16) {

            errorPassword.value = "Password length can\\'t be longer than 16 characters."

        } else {
            errorPassword.value = ""
            loadFromWebServices()
        }
    }

    fun loadFromWebServices() {

        loginRepository.loadFromWebServices(loginModel)
    }
}