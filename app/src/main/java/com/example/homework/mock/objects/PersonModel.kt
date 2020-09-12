package com.example.homework.mock.objects

import com.example.homework.utility.helper.MockClassObjectGenerator
import com.google.gson.annotations.SerializedName
import io.realm.RealmCollection
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import io.realm.annotations.RealmModule
import java.util.*


open class PersonModel:RealmObject() {

    @SerializedName("errorCode")
    var errorCode: String = ""

    @SerializedName("errorMessage")
    var errorMessage: String = ""

    @SerializedName("user")
    var user: User? = null


}