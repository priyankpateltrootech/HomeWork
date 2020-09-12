package com.example.homework.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homework.network.model.BaseResponse
import com.example.homework.network.webservices.RetrofitCallbackHandler
import retrofit2.Call

/**
 * Created by Aaditya on 05/08/2019.
 *
 * Base class for Webservice Repository Classes. Helps us maintain Android App Arch.
 */
interface BaseRepository<Request, Response> {

    /**
     * Getter method for [RetrofitCallbackHandler] instance in current Repository.
     * Forces us to have CallBackHandler Available in our class.
     */
    val callBackHandler: RetrofitCallbackHandler<Response>

    /**
     * Getter for Response must be available to Our [androidx.lifecycle.ViewModel]
     */
    val responseAsLiveData: LiveData<Response>

    /**
     * [<] for the [androidx.lifecycle.ViewModel] for updating the UI
     * according to loadingLiveData status of current call.
     */
    val loadingLiveData: LiveData<Boolean>

    /**
     * [<] for the [androidx.lifecycle.ViewModel] for notifying user
     * of error from server of current call.
     */
    val serverError: LiveData<String>

    /**
     * Get Response code from response, just in case we need it.
     **/
    fun getResponseCode(): String

    /**
     * Make REST API Call.
     *
     * @param request Server Request to be made
     */
    fun loadFromWebServices(request: Request)

    fun loadFromWebServices(requestCall: Call<Response>, startLoading: Boolean)

    fun setMajorErrorMutableLiveData(majorErrorLiveData: MutableLiveData<String>)

    /**
     * Allowing [androidx.lifecycle.ViewModel] to set loadingLiveData status.
     *
     * @param loadingStatus [<] which will get loadingLiveData status of current call.
     */
    fun setLoadingMutableLiveData(loadingStatus: MutableLiveData<Boolean>)

    /**
     * Allowing [androidx.lifecycle.ViewModel] to get Error thrown in API Call.
     *
     * @param throwableLiveData [<] will get notified if Exception is
     * thrown by the server call. Least used
     * but can come in handy in few scenarios.
     */
    fun setThrowableMutableLiveData(throwableLiveData: MutableLiveData<Throwable>)

    /**
     * Allowing [androidx.lifecycle.ViewModel] to set custom server error live-data.
     *
     * @param serverError [<] which will get loadingLiveData status of current call.
     */
    fun setServerErrorMutableLiveData(serverError: MutableLiveData<String>)
}
