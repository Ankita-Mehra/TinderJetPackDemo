package com.softradix.tinderjetpackdemo.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

// function for call activity
fun Activity.callActivity(context: ComponentActivity, activity: ComponentActivity) {
    startActivity(
        Intent(context, activity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
    context.finishAffinity()
}

// show toast on activity
fun Activity.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}