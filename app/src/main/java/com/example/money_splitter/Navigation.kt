package com.example.money_splitter

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.money_splitter.database.CommunityEvent
import com.example.money_splitter.database.CommunityState
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.screens.CommunityScreen
import com.example.money_splitter.screens.DetailScreen
import com.example.money_splitter.screens.ExpenseDetailScreen
import com.example.money_splitter.screens.ExpenseScreen

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
        composable(route = Screen.ExpenseScreen.route) {
            val community = stateCom.communities.find { it.name == stateCom.name }
            val participants = community?.participants ?: emptyList()
            ExpenseScreen(state = stateExp, onEvent = onEventExp, nameCommunity = stateCom.name, participants = participants, navController = navController)
        }

        composable(route = Screen.ExpenseDetailScreen.route) {
            val selectedExpense = stateExp.selectedExpense
            if (selectedExpense != null) {
                ExpenseDetailScreen(navController = navController, expense = selectedExpense)
            }
        }

        composable(route = Screen.DetailScreen.route) {
            val community = stateCom.communities.find { it.name == stateCom.name }
            val participants = community?.participants ?: emptyList()
            val expenses = stateExp.expenses.filter { it.community == stateCom.name }
            DetailScreen(navController = navController, community = community, participants = participants, expenses = expenses)
        }
    }
}