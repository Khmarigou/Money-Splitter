package com.example.money_splitter

sealed class Screen(val route:String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object ExpenseScreen : Screen("expense_screen")
    object ExpenseDetailScreen : Screen("expense_detail_screen")
}