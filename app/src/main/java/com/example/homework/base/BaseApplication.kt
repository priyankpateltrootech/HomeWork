package com.example.homework.base

import android.app.Application
import com.example.homework.base.app.background.AppExecutors
import com.example.homework.base.app.internet.InternetStatusLiveData
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Aaditya on 01/08/2019.
 */
class BaseApplication : Application() {

    lateinit var internetStatusLiveData: InternetStatusLiveData

    override fun onCreate() {
        super.onCreate()

        internetStatusLiveData = InternetStatusLiveData(this)
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("myrealm.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    companion object {
        val appExecutors: AppExecutors =
            AppExecutors()
    }

}