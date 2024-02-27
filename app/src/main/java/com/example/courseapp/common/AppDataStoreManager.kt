package com.example.courseapp.common

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import kotlin.math.log

class AppDataStoreManager @Inject constructor(private val dataStore:DataStore<Preferences>) {

    private object DataStoreKeys {
        const val USER_TOKEN = "USER_TOKEN"
        val userToken = stringPreferencesKey(USER_TOKEN)

        const val VERIFICATION_ID = "VERIFICATION_ID"
        val verificationId = stringPreferencesKey(VERIFICATION_ID)

        const val USER_ID = "USER_ID"
        val userId = intPreferencesKey(USER_ID)
    }

    suspend fun saveToken(token:String){
        dataStore.edit {preferences ->
            preferences[DataStoreKeys.userToken] = token
        }
    }

    val currentToken:Flow<String?> = dataStore.data.catch {exception->
        if (exception is IOException){
            Log.d("DataStore", "Exception: ${exception.message.toString()} ")
            emit(emptyPreferences())
        }else{
            Log.d("DataStore", "Exception2: ${exception.message.toString()} ")
            throw exception

        }
    }.map {preferences ->
        val token = preferences[DataStoreKeys.userToken] ?: ""
        token
    }

    suspend fun clearToken(){
        Log.d("ErrorSkip", "clearToken:")
        dataStore.edit {preferences ->
            preferences.remove(DataStoreKeys.userToken)
        }
    }


    suspend fun saveVerification(verification:String){
        dataStore.edit {preferences ->
            preferences[DataStoreKeys.verificationId] = verification
        }
    }

    val currentVerification:Flow<String?> = dataStore.data.catch { exception->
        if (exception is IOException){
            Log.d("DataStore", "Exception: ${exception.message.toString()} ")
            emit(emptyPreferences())
        }else{
            Log.d("DataStore", "Exception2: ${exception.message.toString()} ")
            throw exception

        }
    }.map {preferences ->
        val verificationId = preferences[DataStoreKeys.verificationId] ?: ""
        verificationId
    }

    suspend fun clearVerification(){
        dataStore.edit {preferences ->
            preferences.remove(DataStoreKeys.verificationId)
        }
    }

    suspend fun saveUserId(userId:Int){
        dataStore.edit {preferences ->
            preferences[DataStoreKeys.userId] = userId
        }
    }

    val currentUserId:Flow<Int?> = dataStore.data.catch {exception->
        if (exception is IOException){
            Log.d("DataStore", "Exception: ${exception.message.toString()} ")
            emit(emptyPreferences())
        }else{
            Log.d("DataStore", "Exception2: ${exception.message.toString()} ")
            throw exception

        }
    }.map {preferences ->
        val userId = preferences[DataStoreKeys.userId]
        userId
    }

    suspend fun clearUserId(){
        dataStore.edit {preferences ->
            preferences.remove(DataStoreKeys.userId)
        }
    }
}