package com.example.money_splitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.money_splitter.database.CommunityDatabase
import com.example.money_splitter.database.ExpenseDatabase
import com.example.money_splitter.ui.theme.CommunityViewModel
import com.example.money_splitter.ui.theme.ExpenseViewModel
import com.example.money_splitter.ui.theme.MoneySplitterTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExpenseDatabase::class.java,
            "expenses.db"
        ).build()
    }

    private val Comdb by lazy {
        Room.databaseBuilder(
            applicationContext,
            CommunityDatabase::class.java,
            "community.db"
        ).build()
    }

    private val ExpviewModel by viewModels<ExpenseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExpenseViewModel(applicationContext, db.dao) as T
                }
            }
        }
    )

    private val ComviewModel by viewModels<CommunityViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CommunityViewModel(applicationContext, Comdb.CommunityDAO()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneySplitterTheme {
                val stateCom by ComviewModel.state.collectAsState()
                val stateExp by ExpviewModel.state.collectAsState()
                Navigation(stateExp = stateExp, stateCom = stateCom, onEventExp = ExpviewModel::onEvent, onEventCom = ComviewModel::onEvent, )
            }
        }
    }
}