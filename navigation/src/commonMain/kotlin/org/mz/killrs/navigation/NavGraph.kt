package org.mz.killrs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.mz.admin.AdminPanelScreen
import org.mz.home.HomeGraphScreen
import org.mz.killrs.DetailsScreen
import org.mz.killrs.auth.AuthenticationScreen
import org.mz.killrs.manage_product.ManageProductScreen
import org.mz.killrs.profile.ProfileScreen
import org.mz.killrs.shared.navigation.Screen

@Composable
fun SetupNavGraph(
    startDestination: Screen = Screen.Auth
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Auth
    ) {
        composable<Screen.Auth> {
            AuthenticationScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo<Screen.Auth> { inclusive = true }
                    }
                },
            )
        }
        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo<Screen.HomeGraph> { inclusive = true }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                },
                navigateToAdmin = {
                    navController.navigate(Screen.Admin)
                },
                navigateToDetails = { productId ->
                    navController.navigate(Screen.Details(id = productId))
                }
            )
        }
        composable<Screen.Profile> {
            ProfileScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Screen.Admin> {
            AdminPanelScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToManageProduct = { id ->
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }
        composable<Screen.ManageProduct> {
            ManageProductScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToEdit = {
                    navController.navigate(Screen.ManageProduct)
                }
            )
        }
        composable<Screen.Details> {
            DetailsScreen(
                navigateBack = {
                    navController.navigateUp()
                })

        }
    }
}
