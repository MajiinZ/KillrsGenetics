package org.mz.killrs.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.mz.admin.AdminPanelScreen
import org.mz.home.HomeGraphScreen
import org.mz.killrs.CheckoutScreen
import org.mz.killrs.DetailsScreen
import org.mz.killrs.PaymentCompleted
import org.mz.killrs.auth.AuthenticationScreen
import org.mz.killrs.category_search.CategorySearchScreen
import org.mz.killrs.manage_product.ManageProductScreen
import org.mz.killrs.profile.ProfileScreen
import org.mz.killrs.shared.domain.ProductCategory
import org.mz.killrs.shared.navigation.Screen

@Composable
fun SetupNavGraph(
    startDestination: Screen = Screen.Auth
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeGraph
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
                },
                navigateToAdmin = {
                    navController.navigate(Screen.Admin)
                },
                navigateToDetails = { productId ->
                    navController.navigate(Screen.Details(id = productId))
                },
                navigateToCategorySearch = { categoryName ->
                    navController.navigate(Screen.CategorySearch(categoryName))
                },
                navigateToCart = {
                    navController.navigate(Screen.Cart)
                },
                navigateToCheckout = { totalAmount ->
                    navController.navigate(Screen.Checkout(totalAmount))
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
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }

        composable<Screen.Checkout> { backStackEntry ->
            val checkoutArgs = backStackEntry.toRoute<Screen.Checkout>()
            val totalAmount = checkoutArgs.totalAmount.toDoubleOrNull() ?: 0.0

            CheckoutScreen(
                totalAmount = totalAmount,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToPaymentCompleted = { isSuccess, error ->
                    navController.navigate(
                        Screen.PaymentCompleted(
                            isSuccess = isSuccess,
                            error = error
                        )
                    )
                }
            )
        }

        composable<Screen.Details> {
            DetailsScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToCart = {
                    navController.navigateUp()
                }
            )
        }
        composable<Screen.CategorySearch> {
            val category = it.toRoute<Screen.CategorySearch>().category?.let { it1 ->
                ProductCategory.valueOf(
                    it1
                )
            }
            if (category != null) {
                CategorySearchScreen(
                    category = category,
                    navigateToDetails = { id ->
                        navController.navigate(Screen.Details(id.toString()))
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }

        composable<Screen.PaymentCompleted> {
            val isSuccess = it.toRoute<Screen.PaymentCompleted>().isSuccess
            val error = it.toRoute<Screen.PaymentCompleted>().error
            PaymentCompleted(
                isSuccess = isSuccess,
                error = error,
                navigateBack = {
                    navController.navigate(Screen.HomeGraph){
                        launchSingleTop = true
                        //clear backstack completly
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

