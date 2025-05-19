package com.example.checkingagp.room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.checkingagp.R
import com.example.checkingagp.notification.AddTaskDialog
import com.example.checkingagp.ui.theme.incompleteTaskColor
import com.example.checkingagp.ui.theme.incompleteTaskHighPriority
import com.example.checkingagp.ui.theme.incompleteTaskLowPriority
import com.example.checkingagp.ui.theme.incompleteTaskMediumPriority
import com.example.checkingagp.ui.theme.tasCompletedHighPriority
import com.example.checkingagp.ui.theme.taskCompletedColor
import com.example.checkingagp.ui.theme.taskCompletedLowPriority
import com.example.checkingagp.ui.theme.taskCompletedMediumPriority


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
                            Text("Delete", color = Color.Red)
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
fun TaskItem(task: Task,
                  onCheckedChange: (Boolean) -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) taskCompletedColor else incompleteTaskColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.taskTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Priority : " +
                    when(task.taskPriority){
                        0 -> "Low"
                        1 -> "Medium"
                        else -> "High"
                    },
                    color =  when(task.taskPriority){
                        0 -> if(task.isCompleted) taskCompletedLowPriority else incompleteTaskLowPriority
                        1 -> if(task.isCompleted) taskCompletedMediumPriority else incompleteTaskMediumPriority
                        else -> if(task.isCompleted) tasCompletedHighPriority else incompleteTaskHighPriority
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }


                Icon(
                    painter = painterResource(if(task.isCompleted) R.drawable.taskcomplete else R.drawable.taskincomplete),
                    contentDescription = null,
                    tint = if(task.isCompleted) Color.Black else Color.Red,
                    modifier = Modifier.size(32.dp).clickable {
                        onCheckedChange(!task.isCompleted)
                    }
                )
        }
    }
}