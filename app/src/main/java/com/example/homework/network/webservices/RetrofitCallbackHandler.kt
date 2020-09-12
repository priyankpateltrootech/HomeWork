package com.example.homework.network.webservices

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homework.R
import com.example.homework.network.model.BaseResponse
import com.example.homework.network.model.ServerErrorModel
import com.example.homework.utility.helper.Nondh
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.nio.charset.StandardCharsets

/**
 * Created by Aaditya on 2019/05/17 13:40
 *
 *
 * Here type [ResponseType] is type of data we expect to get from Web Services.
 *
 * @param application: For log prints and fetching ResString with .getString(R....)
 * @param typeName: For log prints purpose only. Can be omitted, recommended to not to do so.
 */
@Suppress("unused")
class RetrofitCallbackHandler<ResponseType>(
    private val application: Application,
    private val typeName: String
) :

    Callback<ResponseType> {

    private var responseCode: String = "01"

    /**
     * [MutableLiveData] of the Type [ResponseType]. Type ResponseType is our Response from the server. We will look
     * changes into our [androidx.lifecycle.ViewModel] or [androidx.lifecycle.AndroidViewModel]
     * classes.
     *
     *
     * The reason behind choosing [MutableLiveData] here and just [LiveData] in our Sub-Classes
     * is that [LiveData] will only be modified in this part of the stream. Others (especially our
     * [androidx.lifecycle.ViewModel](s) and [android.app.Activity](ies) will only observe the
     * data and reflect the changes accordingly.
     */
    private val liveData = MutableLiveData<ResponseType>()

    /**
     * Throwable in case of error response.
     */
    private var throwableMutableLiveData = MutableLiveData<Throwable>()

    /**
     * Validation error live data from server
     */
    private var serverError = MutableLiveData<String>()

    /**
     * Major error should be displayed all over screen. View should be hidden entirely.
     */
    private var majorError = MutableLiveData<String>()

    /**
     * Helps us to know whether we need to show loadingLiveData widgets or not.
     */
    private var loadingStatus = MutableLiveData<Boolean>()

    /**
     * Called when received successful response from server.
     * */
    override fun onResponse(
        call: Call<ResponseType>,
        response: Response<ResponseType>
    ) {

        val fileName = typeName.split("[.]")[typeName.split("[.]").lastIndex] + ".txt"

        /**This is holy grail for have a crash-less application. Most of our application crashes occur
         * due to Backend developer's "mistake" of sending String instead of integer. (AND WHAT NOT!)
         *
         * Every server call that is made goes through this handled pipeline which allows us to have less crashes
         * and may be just have data not displayed. This also allows us to focus more on our logic.
         * */
        try {

            responseCode = response.code().toString()

            when (responseCode) {

                "200" -> {
                    response.body()?.let { liveData.postValue(it) }
                }

                "401", "404", "422", "500" -> {

                    response.errorBody()?.let {

                        val source = it.source()
                        source.request(java.lang.Long.MAX_VALUE)
                        val buffer = source.buffer()
                        val responseErrorBodyString =
                            buffer.clone().readString(StandardCharsets.UTF_8)

                        val message =
                            Gson().fromJson(responseErrorBodyString, ServerErrorModel::class.java)
                                .message

                        serverError.postValue(message)

                    }
                }

                else -> {

                    throw RuntimeException("New type of response has arrived!")
                }
            }

        } catch (e: Exception) {

            Nondh.d(TAG, "++++++++++ Something's not right with the server response +++++++++++")
            e.printStackTrace()
        }

//        serverResponseLogger.writeResponse(fileName!!, call, response)

        loadingStatus.postValue(false)

    }

    /**
     * Function is called when Retrofit request fails. Here we have stopped loading live data.
     */
    override fun onFailure(call: Call<ResponseType>, t: Throwable) {

        t.printStackTrace()

        if (!call.isCanceled) {

            loadingStatus.postValue(false)

        }

        if (t is SocketTimeoutException) {

            serverError.postValue(application.getString(R.string.request_timed_out))
        }

        throwableMutableLiveData.postValue(t)

    }

    fun getResponseCode(): String {
        return responseCode.toString()
    }

    fun getLiveData(): LiveData<ResponseType> {
        return liveData
    }

    fun getThrowableLiveData(): LiveData<Throwable> {
        return throwableMutableLiveData
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return loadingStatus
    }

    fun getServerErrorLiveData(): LiveData<String> {
        return serverError
    }

    fun getMajorError(): MutableLiveData<String> {
        return majorError
    }

    fun setLoading(b: Boolean) {
        if (loadingStatus.value != b)
            loadingStatus.postValue(b)
    }

    fun setLoadingStatusLiveData(loadingStatus: MutableLiveData<Boolean>) {
        this.loadingStatus = loadingStatus
    }

    fun setThrowableLiveData(throwableMutableLiveData: MutableLiveData<Throwable>) {

        this.throwableMutableLiveData = throwableMutableLiveData
    }

    fun setMajorError(serverError: MutableLiveData<String>) {
        this.majorError = serverError
    }

    fun showMajorError(string: String) {
        majorError.postValue(string)
    }

    fun setServerError(serverError: MutableLiveData<String>) {
        this.serverError = serverError
    }

    fun showServerError(string: String) {
        serverError.postValue(string)
    }

    companion object {

        const val TAG = "RetrofitCallbackHandler"
    }
}