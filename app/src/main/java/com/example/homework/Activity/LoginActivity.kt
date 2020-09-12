package com.example.homework.Activity


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.homework.R
import com.example.homework.base.BaseActivity
import com.example.homework.databinding.ActivityMainBinding
import com.example.homework.handler.LoginActivityHandler
import com.example.homework.mock.objects.PersonModel
import com.example.homework.viewmodel.LoginViewModel
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class LoginActivity : BaseActivity() {

    lateinit var loginBinding: ActivityMainBinding
    lateinit var loginViewModel: LoginViewModel
    lateinit var loginActivityHandler: LoginActivityHandler
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun bindContentView() {
        loginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        loginBinding.lifecycleOwner = this
    }

    override fun initializeViewModelAndHandlers() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginActivityHandler = LoginActivityHandler(application)

        loginBinding.viewModel = loginViewModel
        loginBinding.handler = loginActivityHandler
        Realm.init(application)
        realm = Realm.getDefaultInstance()
    }

    override fun initializeMembers() {

    }

    override fun observeLiveData() {

        loginViewModel.responseLiveData.observe(this, Observer { userModel ->

            run {

                tvUsername.setText("")
                tvPassword.setText("")
                tvUsername.requestFocus()


                realm.beginTransaction()
                realm.insertOrUpdate(userModel)
                realm.commitTransaction()
                Toast.makeText(application, "Login successfully.", Toast.LENGTH_SHORT).show()


                loginViewModel.isLoggedIn.value = true
            }
        })


    }

}
