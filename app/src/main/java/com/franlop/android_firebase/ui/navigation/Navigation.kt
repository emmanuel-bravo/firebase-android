package com.franlop.android_firebase.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.franlop.android_firebase.ui.screens.HomeScreen
import com.franlop.android_firebase.ui.screens.Screen
import com.franlop.android_firebase.ui.screens.auth.ForgotPasswordScreen
import com.franlop.android_firebase.ui.screens.auth.LoginScreen
import com.franlop.android_firebase.ui.screens.auth.SignUpScreen
import com.franlop.android_firebase.utils.AnalyticsManager

@Composable
fun Navigation(context: Context, navController: NavHostController = rememberNavController()) {
    var analytics: AnalyticsManager = AnalyticsManager(context)

    Screen {
        NavHost(
            navController = navController,
            startDestination = Routes.Login.route
        ) {
            composable(Routes.Login.route) {
                LoginScreen(
                    analytics = analytics,
                    navigation = navController,
                )
            }
            composable(Routes.Home.route) {
                HomeScreen(
                    analytics = analytics,
                    navigation = navController)
            }
            composable(Routes.SignUp.route) {
                SignUpScreen(
                    analytics = analytics,
                    navigation = navController
                )
            }
            composable(Routes.ForgotPassword.route) {
                ForgotPasswordScreen(
                    analytics = analytics,
                    navigation = navController
                )
            }
        }
    }
}