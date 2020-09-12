package com.example.homework.base.app.internet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.homework.base.BaseApplication
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Created by Aaditya on 01/08/2019.
 */
class InternetStatusLiveData(private val application: BaseApplication) :
    LiveData<InternetStatus>() {

    private val connectivityManager: ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val callback: ConnectivityManager.NetworkCallback?

    private val networkRequest: NetworkRequest?

    private val broadcastReceiver: BroadcastReceiver?

    private var registered = false

//    override fun onInactive() {
//        unRegisterCallbacks()
//        super.onInactive()
//    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        unRegisterCallbacks()
    }


    /**If Version is above Lollipop use latest else register broadcast receiver
     * with action*/
    init {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            callback = object : ConnectivityManager.NetworkCallback() {

                override fun onCapabilitiesChanged(
                    network: Network?,
                    networkCapabilities: NetworkCapabilities?
                ) {

                    checkNetworkState(getNetworkType(network))
                }

                override fun onLost(network: Network?) {

                    checkNetworkState(getNetworkType(network))
                }

                override fun onAvailable(network: Network?) {

                    checkNetworkState(getNetworkType(network))
                }
            }

            networkRequest = NetworkRequest.Builder().build()

            broadcastReceiver = null

        } else {

            callback = null

            networkRequest = null

            broadcastReceiver = object : BroadcastReceiver() {

                @Suppress("DEPRECATION")
                override fun onReceive(context: Context?, intent: Intent?) {

                    val type: Int

                    type = if (intent != null && intent.extras != null) {

                        val activeNetwork =
                            intent.extras?.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo

                        getNetworkType(activeNetwork.type)

                    } else {

                        InternetStatus.Type.NONE
                    }

                    checkNetworkState(type)
                }
            }
        }

        registerCallbacks()
    }

//    override fun onActive() {
//        super.onActive()
//        registerCallbacks()
//    }

    private fun unRegisterCallbacks() {

        if (!registered) return

        registered = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManager.unregisterNetworkCallback(callback)

        } else {

            application.unregisterReceiver(broadcastReceiver)
        }
    }

    fun tryAgain(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (connectivityManager.allNetworks.isNotEmpty()) {

                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.allNetworks[0])

                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

                } else {

                    val activeNetworkInfo = connectivityManager.activeNetworkInfo

                    activeNetworkInfo != null

                            && activeNetworkInfo.isConnected
                }
            } else {

                return false
            }
        }

        return false
    }

    @Suppress("DEPRECATION")
    private fun registerCallbacks() {

        if (registered) return

        registered = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManager.registerNetworkCallback(networkRequest, callback)

            if (connectivityManager.allNetworks.isNotEmpty())
                checkNetworkState(getNetworkType(connectivityManager.allNetworks[0]))

        } else {

            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

            application.registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    private fun checkNetworkState(connectionType: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            val isConnected =
                capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

            setInternetStatus(InternetStatus(isConnected, connectionType))

        } else {

            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            val bool = activeNetworkInfo != null

                    && activeNetworkInfo.isConnected

            postOnNetwork(bool, connectionType)
        }
    }

    private fun setInternetStatus(status: InternetStatus) {

        if (status != value)

            postValue(status)
    }

    private fun postOnNetwork(tillNow: Boolean, connectionType: Int) {

        if (!tillNow) {

            val status = InternetStatus(tillNow, connectionType)

            setInternetStatus(status)

            return
        }

        BaseApplication.appExecutors.networkIO().execute {

            Socket().use { socket ->
                try {
                    socket.connect(InetSocketAddress("8.8.8.8", 53), 1000)
                    val status = InternetStatus(tillNow, connectionType)
                    if (status != value)
                        setInternetStatus(status)
                } catch (e: IOException) {
                    // Either we have a timeout or unreachable host or failed DNS lookup
                    println(e)
                    val status = InternetStatus(false, connectionType)
                    if (status != value)
                        setInternetStatus(status)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getNetworkType(network: Network?): Int {

        val capabilities = connectivityManager.getNetworkCapabilities(network)

        if (network == null || capabilities == null) {

            return InternetStatus.Type.NONE
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

            return InternetStatus.Type.CELLULAR
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

            return InternetStatus.Type.WIFI
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {

            return InternetStatus.Type.BLUETOOTH
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

            return InternetStatus.Type.ETHERNET
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {

            return InternetStatus.Type.VPN
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN)) {

            return InternetStatus.Type.LOWPAN
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)) {

            return InternetStatus.Type.WIFI_AWARE
        }

        return InternetStatus.Type.OTHER
    }

    @Suppress("DEPRECATION")
    private fun getNetworkType(networkType: Int?): Int {

        return when (networkType) {

            ConnectivityManager.TYPE_WIFI -> InternetStatus.Type.WIFI
            ConnectivityManager.TYPE_MOBILE -> InternetStatus.Type.CELLULAR
            ConnectivityManager.TYPE_VPN -> InternetStatus.Type.VPN

            ConnectivityManager.TYPE_WIMAX -> InternetStatus.Type.WIFI_AWARE

            ConnectivityManager.TYPE_BLUETOOTH -> InternetStatus.Type.BLUETOOTH

            ConnectivityManager.TYPE_ETHERNET -> InternetStatus.Type.ETHERNET

            else -> InternetStatus.Type.OTHER
        }
    }
}