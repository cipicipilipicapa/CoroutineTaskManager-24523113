package com.example.coroutinetaskmanager.ui

import com.example.coroutinetaskmanager.data.Task

data class TasksUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val announcement: String = "",
    val courseCount: Int? = null,
    val errorMessage: String? = null,
    val tapCount: Int = 0,
    val isSorted: Boolean = false
)
