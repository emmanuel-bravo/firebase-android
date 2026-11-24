package com.franlop.android_firebase.ui.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.franlop.android_firebase.ui.navigation.Routes
import com.franlop.android_firebase.ui.theme.Purple40
import com.franlop.android_firebase.utils.AnalyticsManager
import com.franlop.android_firebase.utils.AuthManager
import com.franlop.android_firebase.utils.AuthRes
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(analytics: AnalyticsManager, auth: AuthManager, navigation: NavController) {
    analytics.logScreenView(screenName = Routes.ForgotPassword.route)

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Olvidó su contraseña",
            style = TextStyle(fontSize = 32.sp, color = Purple40)
        )
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            label = { Text(text = "Correo electrónico") },
            value = email,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch {
                        resetPassword(email, auth, analytics, context, navigation)
                    }
                }
            ),
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        resetPassword(email, auth, analytics, context, navigation)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Recuperar contraseña")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = { navigation.popBackStack() }) {
            Text(
                text = "Volver a iniciar sesión",
                style = TextStyle(
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Purple40
                )
            )
        }
    }
}

private suspend fun resetPassword(
    email: String,
    auth: AuthManager,
    analytics: AnalyticsManager,
    context: Context,
    navigation: NavController
) {
    val cleanEmail = email.trim()
    if (cleanEmail.isNotEmpty()) {
        when (val result = auth.sendPasswordResetEmail(cleanEmail)) {
            is AuthRes.Success -> {
                analytics.logButtonClicked("Click: Recuperar contraseña")
                Toast.makeText(
                    context,
                    "Correo de recuperación enviado. Revisa tu bandeja de entrada.",
                    Toast.LENGTH_LONG
                ).show()
                navigation.popBackStack()
            }
            is AuthRes.Error -> {
                analytics.logError("Error ForgotPassword: ${result.errorMessage}")
                Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        Toast.makeText(context, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
    }
}
