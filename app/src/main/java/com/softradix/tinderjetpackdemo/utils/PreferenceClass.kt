package com.softradix.tinderjetpackdemo.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCE_NAME
)
class PreferenceClass(private val context: Context) {

    suspend fun saveString(key: Preferences.Key<String>, value: String) {
//        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun clearPreferenceStorage() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun clearDataWithKey() {
        context.dataStore.edit {
            it.remove(TOKEN)
        }
    }

    suspend fun getString(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return (preferences[dataStoreKey] ?: "")
    }

    fun readString(key: String): Flow<String?> {
        val dataStoreKey = stringPreferencesKey(key)
        return context.dataStore.data.map {
            it[dataStoreKey]
        }
    }
    /*  val lastPlayedSong: Flow<String> = context.dataStore.data
          .map { preferences ->
              preferences[stringPreferencesKey(key)] ?: ""
          }*/

    companion object {
        val TOKEN = stringPreferencesKey("token")
    }


    fun getToken() = context.dataStore.data.map {
        it[TOKEN] ?: ""
    }

    val tokenValue: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey(Constants.TOKEN)] ?: ""
        }


    suspend fun <T> setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    }

    fun <T> getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return context.dataStore.data
            /* .catch { exception ->
             // dataStore.data throws an IOException when an error is encountered when reading data
             if (exception is IOException) {
                 // we try again to store the value in the map operator
                 emit(emptyPreferences())
             } else {
                 throw exception
             }
         }*/
            .map { preferences ->
                // return the default value if it doesn't exist in the storage
                preferences[key] ?: defaultValue
            }
    }
}