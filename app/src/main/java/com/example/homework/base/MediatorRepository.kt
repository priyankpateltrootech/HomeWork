package com.example.homework.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homework.network.model.BaseResponse
import com.example.homework.network.webservices.RetrofitCallbackHandler
import retrofit2.Call

/**
 * Created by Aaditya on 08/08/2019
 *
 * This repository is created to have less burden on particular component repository like the one
 * we would create for login or fetching some list.
 *
 * Only responsibility of that repository will be to send different call for different type. All boilerplate code
 * is now moved to this MediatorRepository.
 *
 * @property Request type of model that will be used for server call.
 * @property Response type of model we expect this call to return.
 *
 * [RetrofitCallbackHandler] for handling [Response].
 */
abstract class MediatorRepository<Request, Response>(final override val callBackHandler: RetrofitCallbackHandler<Response>) :

    BaseRepository<Request, Response> {

    override val responseAsLiveData: LiveData<Response> = callBackHandler.getLiveData()

    private val throwableLiveData: LiveData<Throwable> = callBackHandler.getThrowableLiveData()

    override val serverError: LiveData<String> = callBackHandler.getServerErrorLiveData()

    /**
     * A [Boolean] [LiveData] for loading status. It is immutable so we can just observe it and reflect
     * status in our view e.g. Show/Hide Progress Bar.
     */
    override val loadingLiveData: LiveData<Boolean> = callBackHandler.getLoadingLiveData()

    /**Get response code for server call, this is usually not used in our [android.view.View]s (i.e. [android.app.Activity] or [androidx.fragment.app.Fragment]
     * but we can have it just in case we need it for something.
     */
    override fun getResponseCode(): String {
        return callBackHandler.getResponseCode()
    }

    /**
     * Send boolean to set loading live data value to true.
     */
    override fun loadFromWebServices(
        requestCall: Call<Response>,
        startLoading: Boolean
    ) {

        setLoading(startLoading)

        requestCall.enqueue(callBackHandler)
    }

    /**
     * Major error live data to be observed in view for internet errors, no data available etc.
     */
    override fun setMajorErrorMutableLiveData(majorErrorLiveData: MutableLiveData<String>) {
        callBackHandler.setMajorError(majorErrorLiveData)
    }

    /**
     * We have loading [loadingLiveData] which is immutable but in some cases we need to make multiple
     * call in one page. If we have same progress display mechanism for every call then it would be
     * better to have one mutable live data give to all [RetrofitCallbackHandler]s of our server calls.
     *
     * If progress should not be overridden by other call then it is recommended to use multiple [loadingLiveData]s
     * and then have a [androidx.lifecycle.MediatorLiveData] which observes all of them in one place.
     */
    override fun setLoadingMutableLiveData(loadingStatus: MutableLiveData<Boolean>) {
        callBackHandler.setLoadingStatusLiveData(loadingStatus)
    }

    override fun setThrowableMutableLiveData(throwableLiveData: MutableLiveData<Throwable>) {
        callBackHandler.setThrowableLiveData(throwableLiveData)
    }

    /**
     * Just like [loadingLiveData] we can have single [MutableLiveData] for all server calls so it is observed
     * at same place and handled same way.
     *
     * So, it is recommended to set this ServerErrorMutableLiveData if we have multiple sever calls in same ViewModel
     */
    override fun setServerErrorMutableLiveData(serverError: MutableLiveData<String>) {
        callBackHandler.setServerError(serverError)
    }

    /**
     * If we are only observing [LiveData] of [Response] then we cannot set value to it, so we can request
     * [RetrofitCallbackHandler] to set its value to <code>true</code> or <code>false</code>.
     */
    protected fun setLoading(b: Boolean) {
        callBackHandler.setLoading(b)
    }
}