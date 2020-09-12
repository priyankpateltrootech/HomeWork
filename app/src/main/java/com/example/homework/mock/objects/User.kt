package com.example.homework.mock.objects

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass


open class  User : RealmObject() {
    @SerializedName("userId")
    var userId: String = ""

    @SerializedName("userName")
    var userName: String = ""
}