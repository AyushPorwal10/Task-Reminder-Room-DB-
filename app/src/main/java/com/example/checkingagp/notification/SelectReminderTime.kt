package com.example.checkingagp.notification

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.checkingagp.Helper
import com.example.checkingagp.room.Task
import com.example.checkingagp.room.TaskViewModel
import java.util.Calendar

@Composable
fun AddTaskDialog(onDismiss: () -> Unit, taskViewModel: TaskViewModel) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    val taskTitle = remember {
        mutableStateOf("")
    }
    val choosenPriority = remember {
        mutableIntStateOf(0)
    }
    val timeInMillis = remember { mutableStateOf(10L) }

    var selectedTime by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add Task") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {


                OutlinedTextField(
                    value = taskTitle.value,
                    onValueChange = { taskTitle.value = it },
                    shape = RoundedCornerShape(22.dp),
                    label = {
                        Text("Title")
                    },
                    colors = Helper.TextFieldStyle.myTextFieldColor(),
                    placeholder = {
                        Text("Enter task title")
                    })

                Spacer(modifier = Modifier.height(6.dp))

                Text("Priority", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {
                    Priority("Low",choosenPriority.intValue == 0 ,  onSelected = {
                        choosenPriority.intValue = 0
                    })
                    Priority("Medium",choosenPriority.intValue == 1 ,  onSelected = {
                        choosenPriority.intValue = 1
                    })
                    Priority("High",choosenPriority.intValue == 2 ,  onSelected = {
                        choosenPriority.intValue = 2
                    })
                }
                IconButton(
                    onClick = {
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, selectedHour: Int, selectedMinute: Int ->
                                val hour12 = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                                val amPm = if (selectedHour < 12) "AM" else "PM"
                                selectedTime =
                                    String.format("%d:%02d %s", hour12, selectedMinute, amPm)

                                timeInMillis.value = ScheduleNotification.getTimeInMillis(
                                    selectedHour,
                                    selectedMinute
                                )
                            },
                            hour,
                            minute,
                            false
                        )
                        timePickerDialog.show()
                    },
                ) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {

                    taskViewModel.addTask(
                        Task(
                            taskTitle = taskTitle.value,
                            taskPriority = choosenPriority.intValue,
                            isCompleted = false,
                            isReminderSet = selectedTime.isNotEmpty()
                        ),
                        onTaskAdded = { taskId ->
                            if (taskId != -1L) {
                                ScheduleNotification.scheduleNotification(
                                    context,
                                    taskTitle.value,
                                    timeInMillis.value,
                                    taskId
                                )
                            }
                        }

                    )
                    onDismiss()
                },
                enabled = taskTitle.value.trim().isNotEmpty()
            ) {
                Text("Add Task")
            }
        }
    )
}

@Composable
fun Priority(priority: String, selected: Boolean, onSelected: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 5.dp)
    ) {
        RadioButton(selected = selected, onClick = {
            onSelected()
        })

        Text(priority, modifier = Modifier.clickable {
            onSelected()
        })
    }
}
