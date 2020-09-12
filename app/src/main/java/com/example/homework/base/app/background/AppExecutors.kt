package com.example.homework.base.app.background

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Aaditya on 01/08/2019.
 */
@Suppress("unused")
class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    /**Should use this executor if reading or writing file to device*/
    fun diskIO(): Executor {
        return diskIO
    }

    /**Should use this executor when getting response from server*/
    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}