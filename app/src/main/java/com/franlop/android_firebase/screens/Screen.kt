package com.franlop.android_firebase.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.franlop.android_firebase.ui.theme.AndroidFirebaseTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    AndroidFirebaseTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}