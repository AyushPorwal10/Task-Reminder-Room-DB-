package com.example.checkingagp.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)val taskId : Long = 0,
    val taskTitle : String,
    val taskPriority : Int = 0,
    val isCompleted : Boolean,
    val dateTaskAdded : String = LocalDate.now().toString(),
    val isReminderSet : Boolean
)
