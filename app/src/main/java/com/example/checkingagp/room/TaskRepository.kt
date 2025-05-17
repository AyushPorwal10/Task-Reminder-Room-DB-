package com.example.checkingagp.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDAO: TaskDAO
) {
    suspend fun addTask(task : Task) : Long{
        return taskDAO.addTask(task)
    }

    suspend fun updateTask(task : Task){
        taskDAO.updateTask(task)
    }

    suspend fun deleteTask(taskId : Long){
        taskDAO.deleteTask(taskId)
    }

    suspend fun changeTaskCompletionStatus(taskId : Long , isCompleted : Boolean){
        taskDAO.changeTaskCompletionStatus(taskId , isCompleted)
    }
     fun fetchAllTask() : Flow<List<Task>>{
       return taskDAO.fetchAllTask()
    }
     fun selectTask(taskId : Long) : Flow<Task>{
        return taskDAO.selectTask(taskId)
    }

}