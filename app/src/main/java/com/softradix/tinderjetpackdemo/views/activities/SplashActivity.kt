package com.softradix.tinderjetpackdemo.views.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.softradix.tinderjetpackdemo.utils.PreferenceClass
import com.softradix.tinderjetpackdemo.utils.toast
import kotlinx.coroutines.flow.collect

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = PreferenceClass(this)
//        val tokenString = dataStore.getValueAsFlow(PreferenceClass.PreferencesKeys.TOKEN, false)

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

