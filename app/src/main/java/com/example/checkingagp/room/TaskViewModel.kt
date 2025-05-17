package com.example.checkingagp.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel(){

    private val _allTask = MutableStateFlow<List<Task>>(emptyList())
    val allTask : StateFlow<List<Task>> = _allTask.asStateFlow()

    val tag = "TaskViewModel"
    fun errorHandlingMechanism(block : suspend  () -> Unit ){

        viewModelScope.launch {
            try {
                block()
            }
            catch (e : Exception){
                Log.e(tag , "Error : ${e.message}")
            }
        }
    }
    init {
        errorHandlingMechanism {
            taskRepository.fetchAllTask().collect{
                _allTask.value = it
            }
        }
    }

    fun addTask(task : Task, onTaskAdded : (Long) -> Unit) {
        errorHandlingMechanism {
            val isReminderSet = task.isReminderSet

            val taskId = taskRepository.addTask(task)
            Log.d(tag , "Task Added")

            onTaskAdded(if(isReminderSet) taskId else -1)
        }
    }

    fun changeTaskCompletionStatus(taskId : Long , isCompleted : Boolean){
        errorHandlingMechanism {
            taskRepository.changeTaskCompletionStatus(taskId , isCompleted)
        }
    }

    fun deleteTask(taskId : Long){
        errorHandlingMechanism {
            taskRepository.deleteTask(taskId)
        }
    }
}