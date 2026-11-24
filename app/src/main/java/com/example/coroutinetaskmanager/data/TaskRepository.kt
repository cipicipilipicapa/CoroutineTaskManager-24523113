package com.example.coroutinetaskmanager.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException

class TaskRepository {
    suspend fun getTasks(shouldFail: Boolean = false): List<Task> = withContext(Dispatchers.IO) {
        delay(2000L)
        if (shouldFail) throw IOException("Server is not reachable")
        listOf(
            Task(1, "Write lab report", "Mobile App Development"),
            Task(2, "Read chapter 5", "Data Science"),
            Task(3, "Prepare quiz", "Research Methodology"),
            Task(4, "Create UI prototype", "Human Computer Interaction")
        )
    }


    suspend fun getAnnouncement(): String = withContext(Dispatchers.IO) {
        delay(1000L)
        "Quiz 2 is next Monday"
    }


    // Latihan 9 nomor 1: tambahan asynchronous request
    suspend fun getCourseCount(): Int = withContext(Dispatchers.IO) {
        delay(1500L)
        4
    }
}
