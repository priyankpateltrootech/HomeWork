package com.example.homework.base.app.internet

/**
 * Created by Aaditya on 01/08/2019.
 */
class InternetStatus(val isConnected: Boolean, val connectionType: Int) {

    class Type {

        companion object {

            const val NONE: Int = -1
            const val CELLULAR = 0
            const val WIFI = 1
            const val BLUETOOTH = 2
            const val ETHERNET = 3
            const val VPN = 4
            const val WIFI_AWARE = 5
            const val LOWPAN = 6
            const val OTHER = 7
        }
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) return false

        if (other !is InternetStatus) return false

        return isConnected == other.isConnected && connectionType == other.connectionType
    }

    override fun hashCode(): Int {
        var result = isConnected.hashCode()
        result = 31 * result + connectionType
        return result
    }
}