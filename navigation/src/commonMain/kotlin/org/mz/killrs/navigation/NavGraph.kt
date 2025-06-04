package org.mz.killrs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.mz.killrs.auth.AuthenticationScreen

@Composable
fun SetupNavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Auth
    ){
        composable<Screen.Auth> {
            AuthenticationScreen()
        }
    }
}
