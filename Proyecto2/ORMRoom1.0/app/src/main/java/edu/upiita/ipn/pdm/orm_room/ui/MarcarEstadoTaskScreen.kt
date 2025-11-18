package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskDto
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel

@Composable
fun MarcarEstadoTaskScreen(
    viewModel: TaskViewModel,
    paddingValues: PaddingValues
) {
    // 1. Observa y carga las tareas desde la API
    val tasks by viewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getTasks()
    }

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Marcar Estado de Tarea",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
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
                        // Suponemos que el estado "Completada" existe en tu API
                        checked = task.status.equals("Completada", ignoreCase = true),
                        onCheckedChange = { isChecked ->
                            // 3. Determina el nuevo estado
                            val newStatus = if (isChecked) "Completada" else "Pendiente"

                            // 4. Crea una copia de la tarea (TaskDto) con el estado cambiado
                            val updatedTask = task.copy(status = newStatus)

                            // 5. Llama al ViewModel para actualizar.
                            //    Recuerda que esta función aún no está conectada al backend.
                            viewModel.updateTask(updatedTask)
                        }
                    )
                }
            }
        }
    }
}
