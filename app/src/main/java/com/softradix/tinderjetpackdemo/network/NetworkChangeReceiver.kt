package com.softradix.tinderjetpackdemo.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.softradix.tinderjetpackdemo.utils.ViewUtils

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val status: String? = NetworkUtil.getConnectivityStatusString(context)
        if (status == "3") {
            ViewUtils.showInternetDialog(context)
        } else {
            ViewUtils.hideInternetDialog()
        }
    }
}