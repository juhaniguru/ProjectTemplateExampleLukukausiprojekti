package com.example.projecttemplateexample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projecttemplateexample.models.UserState
import com.example.projecttemplateexample.vm.UsersViewModel

@Composable
fun UsersScreenRoot(modifier: Modifier = Modifier) {
    val vm = hiltViewModel<UsersViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    UsersScreen(state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(modifier: Modifier = Modifier, state: UserState) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Users")
        })
    }) { paddingValues ->
        when {
            state.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                state.error?.let { err ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(err)
                    }
                } ?: LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)) {
                    items(state.users) { user ->
                        Text(user.name)
                    }
                }
            }
        }
    }
}