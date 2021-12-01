package com.softradix.tinderjetpackdemo.di

import android.content.Context
import javax.inject.Singleton


@Singleton
class UserManager(
    private val context: Context
) {
//    private val Context.prefsDataStore by preferencesDataStore(
//        name = Constants.PREFERENCE_NAME
//    )
//
//     suspend fun saveString(key: String, value: String) {
//        val dataStoreKey = stringPreferencesKey(key)
//        context.prefsDataStore.edit { settings ->
//            settings[dataStoreKey] = value
//        }
//    }
//
//     suspend fun readString(key: String): Flow<String?> {
//        val dataStoreKey = stringPreferencesKey(key)
//        val preferences = context.prefsDataStore.data.map { pref->
//            pref[dataStoreKey]
//        }
//        return preferences
//    }
//
//     suspend fun clearPreferenceStorage() {
//        context.prefsDataStore.edit {
//            it.clear()
//        }
//    }
//
//     suspend fun getUserDataStore(): UserManager {
//    return this
//    }
}

interface PreferenceStorage {
    /***
     * clears all the stored data
     */
    suspend fun clearPreferenceStorage()
    suspend fun getUserDataStore() : UserManager
    suspend fun saveString(key:String,value:String)
    suspend fun readString(key:String):String?
}