package com.example.myapitest.navigation

import AddCarScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapitest.ui.screens.DetailsCarScreen
import com.example.myapitest.ui.screens.HomeScreen
import com.example.myapitest.ui.screens.LoginScreen
import com.example.myapitest.ui.viewModel.CarsListViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController, CarsListViewModel()) }
        composable("carDetails/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            if (carId != null) {
                DetailsCarScreen(navController = navController, carId = carId)
            }
        }
        composable("addCar") { AddCarScreen(navController)}
    }
}
