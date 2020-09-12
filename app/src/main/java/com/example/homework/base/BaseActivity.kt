package com.example.homework.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.homework.base.app.internet.InternetStatusLiveData
import androidx.databinding.DataBindingUtil

/**
 * Created by Aaditya on 02/08/2019.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var internetStatusLiveData: InternetStatusLiveData

    private lateinit var majorError: LiveData<String>

    private val refreshLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var autoRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        internetStatusLiveData = (application as BaseApplication).internetStatusLiveData

        // 14-08-2019
        // These four methods are called in sequence de-cluttering our onCreate method
        // Please provide suggestions if this can be done different way.

        bindContentView()

        initializeViewModelAndHandlers()

        initializeMembers()

        observeLiveData()
    }

    /**Bind you Activity's view with [DataBindingUtil]*/
    abstract fun bindContentView()

    /**Initialize your [ViewModel] or [AndroidViewModel] and Handler classes for Databinding events. Make sure to set them in your ViewDataBinding*/
    abstract fun initializeViewModelAndHandlers()

    /**Initialization of member variables go here. Do not hesitate to generalize methods further.*/
    abstract fun initializeMembers()

    /**Observe necessary [LiveData] from your view's [ViewModel]*/
    abstract fun observeLiveData()


}