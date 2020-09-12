package com.example.homework.Activity

import android.app.Application
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration




class MyApplication : Application() {
     fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        Realm.init(this)
         val config = RealmConfiguration.Builder().name("myrealm.realm").build()
         Realm.setDefaultConfiguration(config)
    }
}