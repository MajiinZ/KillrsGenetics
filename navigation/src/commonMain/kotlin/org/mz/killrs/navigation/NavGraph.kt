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
        startDestination = startDestination
    ) {
        // 🔹 Authentication
        composable<Screen.Auth> {
            AuthenticationScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo<Screen.Auth> { inclusive = true }
                    }
                },
            )
        }

        // 🔹 Main app (with nested bottom bar NavHost inside)
        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo<Screen.HomeGraph> { inclusive = true }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                    navController.navigateUp()

                },
                navigateToAdmin = {
                    navController.navigate(Screen.Admin)
                },
                navigateToDetails = { productId ->
                    navController.navigate(Screen.Details(id = productId))
                    navController.navigateUp()

                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // 🔹 Profile
        composable<Screen.Profile> {
            ProfileScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        // 🔹 Admin
        composable<Screen.Admin> {
            AdminPanelScreen(
                navigateBack = { navController.navigateUp() },
                navigateToManageProduct = { id ->
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }

        // 🔹 Manage product
        composable<Screen.ManageProduct> {
            ManageProductScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    // must provide an id here
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }

        // 🔹 Details (from anywhere, including nested HomeGraph)
        composable<Screen.Details> {
            DetailsScreen(
                navigateBack = { navController.navigateUp() },
                navigateToCart = {
                    navController.navigate(Screen.Cart)
                },
            )
        }
    }
}
