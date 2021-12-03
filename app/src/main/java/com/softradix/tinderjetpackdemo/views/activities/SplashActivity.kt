package com.softradix.tinderjetpackdemo.views.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.softradix.tinderjetpackdemo.utils.PreferenceClass
import kotlinx.coroutines.flow.collect

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = PreferenceClass(this) // instance of the datastore class
//        val tokenString = dataStore.getValueAsFlow(PreferenceClass.PreferencesKeys.TOKEN, false)

        // for getting the data from datastore, need to use collect function inside coroutine scope
        lifecycleScope.launchWhenCreated {
            dataStore.getToken().collect {
                val tokenString = it

                Handler(Looper.getMainLooper()).postDelayed({
                    if (tokenString == "1") {
                        startActivity(Intent(this@SplashActivity, DashBoardActivity::class.java))
                        finishAffinity()
                    } else {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finishAffinity()
                    }

                }, 2000)
            }

        }

    }
}

