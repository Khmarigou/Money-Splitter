package com.example.money_splitter.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.money_splitter.Screen
import com.example.money_splitter.database.CommunityEvent
import com.example.money_splitter.database.CommunityState
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.getParticipantsAsString
import kotlin.math.exp

@Composable
fun CommunityScreen(
    state: CommunityState,
    onEvent: (CommunityEvent) -> Unit,
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(CommunityEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Creating community")
            }
        },
        modifier = Modifier.padding(16.dp)
    ){ padding ->
        if(state.isAddingCommunity) {
            AddCommunityDialog(state = state, onEvent = onEvent)
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Community Screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )

            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.communities) { community ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.DetailScreen.route)
                            }
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "${community.name} ", fontSize = 20.sp)
                            Text(text = "Participants : ${community.getParticipantsAsString()}", fontSize = 12.sp)
                        }

                        }
                    }
                }
            }
        }
    }


