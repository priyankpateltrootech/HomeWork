package com.example.homework.data.model

import android.app.Application
import android.preference.PreferenceManager
import com.example.homework.utility.constants.KeyValues
import com.google.gson.annotations.SerializedName

/**
 * Created by Aaditya on 01/08/2019.
 *
 * TODO: Change UserModel as needed to maintain login session, this is just example model.
 */
data class UserModel(

    @SerializedName("errorCode")
    var errorCode: String,

    @SerializedName("errorMessage")
    var errorMessage: String

    ) {

    companion object {

        /**
         * Method for storing [UserModel] to [SharedPreferences]. User model stored as a "USER_MODEL" json
         * with values encrypted.
         * */
        @JvmStatic
        fun store(application: Application, userModel: UserModel) {

            val sp = PreferenceManager.getDefaultSharedPreferences(application)

            val json = userModel.toString()

            sp.edit().putString(KeyValues.USER_MODEL, json).apply()
        }

        /**
         * Method for retrieving [UserModel] from [SharedPreferences].
         *
         *
         * User model stored as a "USER_MODEL" json with its values encrypted is retrieved and decrypted to its
         * original values.
         * */
        @JvmStatic
        fun retrieve(application: Application): String? {

            val sp = PreferenceManager.getDefaultSharedPreferences(application)

            val json = sp.getString(KeyValues.USER_MODEL, null)

            return if (json.isNullOrEmpty()) null
            else
                json
        }

        /**
         * Utility method for checking whether user is logged in. Check if stored file is encrypted.
         * */

        @JvmStatic
        fun logout(application: Application) {

            val sp = PreferenceManager.getDefaultSharedPreferences(application)

            sp.edit().remove(KeyValues.USER_MODEL).apply()
        }
    }
}