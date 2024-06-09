package com.example.money_splitter

sealed class Screen(val route:String) {
    data object MainScreen : Screen("main_screen")
    data object DetailScreen : Screen("detail_screen")
    data object ExpenseScreen : Screen("expense_screen")
    data object ExpenseDetailScreen : Screen("expense_detail_screen")
}