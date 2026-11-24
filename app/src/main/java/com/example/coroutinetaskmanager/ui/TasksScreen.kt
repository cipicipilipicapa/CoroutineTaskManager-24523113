package com.example.coroutinetaskmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TasksScreen(viewModel: TasksViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Coroutine Task Manager", style = MaterialTheme.typography.headlineSmall)
            Text("Kotlin Coroutines • Async loading demo", style = MaterialTheme.typography.bodyMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.loadData() }, enabled = !state.isLoading) { Text("Load") }
                OutlinedButton(onClick = { viewModel.loadData(true) }, enabled = !state.isLoading) { Text("Load (Fail)") }
                OutlinedButton(onClick = viewModel::cancelLoading, enabled = state.isLoading) { Text("Cancel") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = viewModel::onTap) { Text("Tap me: ${state.tapCount}") }
                OutlinedButton(onClick = viewModel::blockMainThread) { Text("Block UI") }
            }
            if (state.isLoading) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    CircularProgressIndicator(Modifier.size(24.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Loading data... please wait")
                }
            }
            state.errorMessage?.let { message ->
                Text(message, color = MaterialTheme.colorScheme.error)
                Button(onClick = { viewModel.loadData() }) { Text("Retry") }
            }
            if (state.announcement.isNotBlank()) {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Announcement", style = MaterialTheme.typography.titleMedium)
                        Text(state.announcement)
                    }
                }
            }
            state.courseCount?.let { count ->
                Text("Course count: $count")
            }
            if (state.tasks.isNotEmpty()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Tasks (${state.tasks.size})", style = MaterialTheme.typography.titleMedium)
                    OutlinedButton(onClick = viewModel::sortTasks) { Text(if (state.isSorted) "Sorted A–Z" else "Sort") }
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                items(state.tasks, key = { it.id }) { task ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(task.title, style = MaterialTheme.typography.titleMedium)
                            Text(task.course, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
