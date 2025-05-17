package com.example.checkingagp.notification

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val timeInMillis = remember { mutableStateOf(10L) }

    val taskDescription = remember { mutableStateOf("") }

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


                OutlinedTextField(
                    value = taskDescription.value,
                    onValueChange = { taskDescription.value = it },
                    colors = Helper.TextFieldStyle.myTextFieldColor(),
                    label = {
                        Text("Description")
                    },
                    shape = RoundedCornerShape(22.dp),
                    placeholder = {
                        Text("Enter task description")
                    })

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
                            taskDescription = taskDescription.value,
                            isCompleted = false,
                            isReminderSet = selectedTime.isNotEmpty()
                        ),
                        onTaskAdded = {taskId->
                            if(taskId != -1L){
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
                enabled = taskTitle.value.trim().isNotEmpty() && taskDescription.value.trim()
                    .isNotEmpty()
            ) {
                Text("Add Task")
            }
        }
    )
}
