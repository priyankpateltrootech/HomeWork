
package com.example.homework.network.webservices

import android.annotation.SuppressLint
import android.text.TextUtils
import com.example.homework.base.BaseApplication
import com.example.homework.utility.helper.Nondh
import com.google.gson.Gson
import okhttp3.Headers
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Aaditya on 06/08/2019.
 */
@SuppressLint("unused")
class ApiClient {

    companion object {




        private val BASE_URL = "http://private-222d3-homework5.apiary-mock.com/api/" // TODO: Add Base URL


        private val WEB_SERVICE_URL = BASE_URL

        private val SOCKET_BASE_URL = ""

        private val SOCKET_PATH = ""

        // TODO: Add common headers like 'app_token', 'device_type' which you have to add to every call.
        private val commonHeaders = Headers.Builder().build()
        //                                  .addHeader("app_token", "70p.$3cr37.123")
        //                                  .addHeader("device", "android")
        //                                  .addHeader("app_id", "3")
        //                                  .build()


        // TODO: Add custom GsonConverterFactory if needed.
        private val factory: Converter.Factory =
            GsonConverterFactory.create()

        private lateinit var retrofit: Retrofit

        private var authorizedRetrofit: Retrofit? = null

        private var currentToken = ""

        private val TIME_OUT_IN_SECONDS: Long =
            20 // TODO: Change Timeout if necessary (default is 10)

        /**
         * Factory built from [Gson] object which makes use of [] utility class
         * for converting null to empty string
         */
        fun getClient(): Retrofit {

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->

                    val newRequest = chain.request().newBuilder()
                        .headers(commonHeaders)
                        .build()

                    chain.proceed(newRequest)
                }
                .readTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .callbackExecutor(BaseApplication.appExecutors.networkIO())
                .addConverterFactory(factory)
                .client(client)
                .build()

            return retrofit
        }

        /**
         * Attach bearer token to Client
         */
        fun getClientWithToken(token: String): Retrofit {

            if (TextUtils.isEmpty(token)) {

                return getClient()
            }

            if (authorizedRetrofit == null || currentToken != token) {

                val authorizedClient = OkHttpClient.Builder().addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .headers(commonHeaders)
                        .addHeader("Authorization", "Bearer $token") // Bearer token here
                        .build()
                    chain.proceed(newRequest)
                }
                    .readTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                    .build()

                authorizedRetrofit = retrofit.newBuilder()
                    .client(authorizedClient)
                    .callbackExecutor(BaseApplication.appExecutors.networkIO())
                    .baseUrl(WEB_SERVICE_URL)
                    .addConverterFactory(factory)
                    .build()
            }

            return authorizedRetrofit ?: let {
                Nondh.d(
                    "\nApi Client",
                    "\n****\n\n\nAuthorised client was null please check\n\n\n****\n"
                )

                getClient()
            }
        }

        /**
         * Make sure that authorized client client is never invoked if user has logged out
         */
        fun logout() {

            authorizedRetrofit = null
        }
    }
}