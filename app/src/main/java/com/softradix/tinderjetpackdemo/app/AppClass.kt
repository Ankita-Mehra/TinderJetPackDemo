package com.softradix.tinderjetpackdemo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//initialise hilt for the very first time.
@HiltAndroidApp
class AppClass : Application()