package com.example.checkingagp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface TaskDAO {
    @Insert
    suspend fun addTask(task : Task) : Long

    @Update
    suspend fun updateTask(task : Task)

    @Query("DELETE FROM task_table WHERE taskId = :taskId")
    suspend fun deleteTask(taskId : Long)

    @Query("Select * from task_table Order By isCompleted ASC")
     fun fetchAllTask() : Flow<List<Task>>

     @Query("Select * from task_table where taskId = :taskId")
     fun selectTask(taskId : Long) : Flow<Task>

     @Query("Update task_table SET isCompleted = :isCompleted where taskId = :taskId")
     suspend fun changeTaskCompletionStatus(taskId : Long , isCompleted : Boolean)




}