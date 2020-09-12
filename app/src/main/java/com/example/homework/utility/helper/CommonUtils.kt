package com.example.homework.utility.helper

import android.annotation.SuppressLint
import android.content.Context

class CommonUtils {

    companion object {

        @SuppressLint("HardwareIds")
        fun getDeviceToken(context: Context): String {
            return android.provider.Settings.Secure.getString(
                context.contentResolver,
                android.provider.Settings.Secure.ANDROID_ID
            )
        }
    }
}