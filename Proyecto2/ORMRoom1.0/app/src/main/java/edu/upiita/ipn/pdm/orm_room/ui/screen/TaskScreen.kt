package edu.upiita.ipn.pdm.orm_room.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskDto
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskRequest
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getTasks()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("📋 Lista de Tareas (desde API)", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        // Campos para nueva tarea
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripcion de tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deadline,
            onValueChange = { deadline = it },
            label = { Text("Fecha límite (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && descripcion.isNotBlank() && deadline.isNotBlank()) {
                    val taskRequest = TaskRequest(
                        name = name,
                        descripcion = descripcion,
                        deadline = deadline
                    )
                    viewModel.addTask(taskRequest)
                    name = ""; descripcion = ""; deadline = "";
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar tarea")
        }

        Spacer(Modifier.height(16.dp))

        // Lista de tareas
        LazyColumn {
            items(tasks) { task ->
                TaskItem(task)
            }
        }
    }
}

@Composable
fun TaskItem(task: TaskDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("🆔 ID: ${task.id ?: "-"}")
            Text("📌 ${task.name}")
            Text(" Descripcion: ${task.descripcion}")
            Text("📅 Deadline: ${task.deadline}")
        }
    }
}
