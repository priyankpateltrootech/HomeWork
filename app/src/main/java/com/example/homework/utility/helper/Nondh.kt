package com.example.homework.utility.helper

import android.util.Log

@Suppress("ConstantConditionIf", "unused")
object Nondh {

    private const val loggingEnabled = true

    @JvmStatic
    fun v(tag: String, msg: String?) {

        if (!loggingEnabled) return

        Log.v(tag, msg)
    }

    @JvmStatic
    fun i(tag: String, msg: String?) {

        if (!loggingEnabled) return

        Log.i(tag, msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String?) {

        if (!loggingEnabled) return

        Log.d(tag, msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String?) {

        if (!loggingEnabled) return

        Log.w(tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String?) {

        if (!loggingEnabled) return

        Log.e(tag, msg)
    }

}