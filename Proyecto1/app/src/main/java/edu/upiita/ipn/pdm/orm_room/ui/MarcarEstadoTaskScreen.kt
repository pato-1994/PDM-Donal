package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel

@Composable
fun UserMostrarEstadoScreen(
    viewModel: UserViewModel,
    paddingValues: PaddingValues
) {
    // 1. Observa y carga las tareas
    val tasks by viewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Marcar Estado de Tarea", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))
        }

        items(tasks) { task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Estado: ${task.status}", style = MaterialTheme.typography.bodyMedium)
                    }

                    // 2. Checkbox para el estado
                    Checkbox(
                        checked = task.status == "Completada",
                        onCheckedChange = { isChecked ->
                            // 3. Determina el nuevo estado
                            val newStatus = if (isChecked) "Completada" else "Pendiente"

                            // 4. Crea una copia de la tarea con el estado cambiado
                            val updatedTask = task.copy(status = newStatus)

                            // 5. Llama al ViewModel para actualizar
                            viewModel.updateTask(updatedTask)
                        }
                    )
                }
            }
        }
    }
}