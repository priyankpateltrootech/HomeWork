package com.example.homework.mock.objects

import com.google.gson.annotations.SerializedName

data class MockUserModel(

    @SerializedName("token")
    var token: String = "hf8347h834ycr3cfmh82374ytfj3894jf289yjt893tjf3tv983ymytcmqoiwaukq39uds7q07gt0adhg",

    @SerializedName("id")
    var id: Int = 1,

    @SerializedName("name")
    var name: String = "John Smith",

    @SerializedName("email")
    var email: String = "john@example.com",

    @SerializedName("birth_date")
    var birthDate: String = "2018-09-09",

    @SerializedName("age")
    var age: Int = 2,

    @SerializedName("profile_image")
    var profileImage: String = "https://dummyimages.com/image.png",

    @SerializedName("google_id")
    var googleId: String = "asdf;lkjasddflkjsdgvjlkdsfljkdfsakljdsfklj",

    @SerializedName("facebook_id")
    var facebookId: String = "asdfasdfatgqerwgggqwqwrf",

    @SerializedName("twitter_id")
    var twitterId: String = "qwveqw rewtvwtrwtw4twret",

    @SerializedName("user_type")
    var userType: String = "TYPE",

    @SerializedName("status")
    var status: String = "Done",

    @SerializedName("state")
    var state: String = "Ohio",

    @SerializedName("city")
    var city: String = "Clevelend",

    @SerializedName("zip_code")
    var zipCode: String = "2335",

    @SerializedName("is_email_verified")
    var isEmailVerified: Boolean = true,

    @SerializedName("login_type")
    var loginType: String = "XXX"


)