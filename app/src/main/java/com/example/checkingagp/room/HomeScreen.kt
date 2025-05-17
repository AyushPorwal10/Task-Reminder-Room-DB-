package com.example.checkingagp.room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.checkingagp.notification.AddTaskDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(taskViewModel: TaskViewModel ) {
    val tasks by taskViewModel.allTask.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddTaskDialog(
            onDismiss = {
                showDialog = false
            },
            taskViewModel
        )
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("My To Do List")
            }
        )
    } , floatingActionButton = {
        FloatingActionButton(onClick = {
            showDialog = true
        }) {
            Icon(imageVector =  Icons.Default.Add , contentDescription = null)
        }
    }) {paddingValues->

        HorizontalDivider(modifier = Modifier.size(1.dp) , color = Color.Black)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(tasks, key = { it.taskId }){task->

                val dismissState = rememberSwipeToDismissBoxState(
                    positionalThreshold = {100f},
                    confirmValueChange = {dismissValue->
                        if(dismissValue == SwipeToDismissBoxValue.EndToStart){
                            taskViewModel.deleteTask(task.taskId)
                            true
                        }
                        else
                            false
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(16.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text("Delete", color = Color.White)
                        }
                    },
                    content = {
                        TaskItem(task = task) { isChecked ->
                            taskViewModel.changeTaskCompletionStatus(task.taskId, isChecked)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit
) {

    Card(colors = CardDefaults.cardColors(
        containerColor = if(task.isCompleted) Color.Green else Color.Red
    )) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = task.taskTitle, style = MaterialTheme.typography.titleMedium)
                Text(text = task.taskDescription, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = if (task.isCompleted) "Completed" else "Pending",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange
            )
        }
    }

}

