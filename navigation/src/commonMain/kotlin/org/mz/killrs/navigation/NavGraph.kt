package org.mz.killrs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.mz.home.HomeGraphScreen
import org.mz.killrs.auth.AuthenticationScreen
import org.mz.killrs.shared.navigation.Screen

@Composable
fun SetupNavGraph(
    startDestination: Screen = Screen.Auth
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Auth
    ){
        composable<Screen.Auth> {
            AuthenticationScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph){
                        popUpTo<Screen.Auth>{inclusive = true }
                    }
                }
            )
        }
        composable<Screen.HomeGraph> {
            HomeGraphScreen()
        }
    }
}
