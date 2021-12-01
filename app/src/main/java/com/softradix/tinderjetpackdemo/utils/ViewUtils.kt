package com.softradix.tinderjetpackdemo.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Window
import android.view.WindowManager
import com.softradix.tinderjetpackdemo.R

object ViewUtils {
    private var mProgressDialog: Dialog? = null

    fun showProgress(mActivity: Activity?) {
        if (mProgressDialog == null && mActivity?.isFinishing == false) {
            mActivity.let {
                mProgressDialog = Dialog(it, android.R.style.Theme_Translucent)
                mProgressDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
                mProgressDialog?.setContentView(R.layout.progress_layout)
                mProgressDialog?.setCancelable(false)
            }
        }

        mProgressDialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        mProgressDialog?.window?.statusBarColor = Color.parseColor("#E2FFFFFF")

        try {
            mProgressDialog?.show()
        } catch (e: WindowManager.BadTokenException) {
            //use a log message
        }
    }


    fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
    }

    fun showInternetDialog(context: Context) {
        mProgressDialog = Dialog(context, android.R.style.Theme_Translucent)
        mProgressDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        mProgressDialog?.setContentView(R.layout.dialog_internet)
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        mProgressDialog?.window?.statusBarColor = Color.parseColor("#80000000")
        mProgressDialog?.show()
    }


    fun hideInternetDialog() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
    }
}