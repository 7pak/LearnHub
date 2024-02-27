package com.example.courseapp.common

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun BackHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit,
    dispatcher: OnBackPressedDispatcher
) {
    val callback = rememberUpdatedState(onBackPressed)

    DisposableEffect(callback) {
        val backCallback = object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                callback.value()
            }
        }

        dispatcher.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}