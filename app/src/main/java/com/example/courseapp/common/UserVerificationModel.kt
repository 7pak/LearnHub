package com.example.courseapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserVerificationModel @Inject constructor(
    private val dataStore: AppDataStoreManager,
) : ViewModel() {


    val tokenFlow: Flow<String?> = dataStore.currentToken
        .flowOn(Dispatchers.Main)
        .buffer(Channel.CONFLATED)

    val verificationIdFlow: Flow<String?> = dataStore.currentVerification
        .flowOn(Dispatchers.Main)
        .buffer(Channel.CONFLATED)

    val userIdFlow: Flow<Int?> = dataStore.currentUserId
        .flowOn(Dispatchers.Main)
        .buffer(Channel.CONFLATED)

    fun saveToken(token: String) {
        viewModelScope.launch {
            dataStore.saveToken(token)
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            dataStore.clearToken()
        }
    }

    fun saveVerification(verificationId: String) {
        viewModelScope.launch {
            dataStore.saveVerification(verificationId)
        }
    }

    fun clearVerification() {
        viewModelScope.launch {
            dataStore.clearVerification()
        }
    }

    fun saveUserId(userId: Int) {
        viewModelScope.launch {
            dataStore.saveUserId(userId)
        }
    }

    fun clearUserId() {
        viewModelScope.launch {
            dataStore.clearUserId()
        }
    }
}
