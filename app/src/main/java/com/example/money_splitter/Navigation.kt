package com.example.money_splitter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.money_splitter.database.CommunityEvent
import com.example.money_splitter.database.CommunityState
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.screens.CommunityScreen
import com.example.money_splitter.screens.DetailScreen
import com.example.money_splitter.screens.ExpenseScreen
import com.example.money_splitter.screens.MainScreen

@Composable
fun Navigation(
    stateCom: CommunityState,
    stateExp: ExpenseState,
    onEventCom: (CommunityEvent) -> Unit,
    onEventExp: (ExpenseEvent) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            CommunityScreen(stateCom, onEventCom, navController = navController)
        }
        composable(route = Screen.DetailScreen.route) {
            ExpenseScreen(state = stateExp, onEvent = onEventExp, nameCommunity = stateCom.name, navController = navController)
        }
    }
}