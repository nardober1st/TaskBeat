package com.comunidadedevspace.taskbeats.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) // This will auto generate a Id for a Task
    val id: Int = 0,
    val title: String,
    val description: String
) : Serializable

