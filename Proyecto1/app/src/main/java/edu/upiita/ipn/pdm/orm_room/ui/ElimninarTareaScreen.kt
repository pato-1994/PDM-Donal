package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel

@Composable
fun UserEliminarTareaScreen(
    viewModel: UserViewModel,
    paddingValues: PaddingValues
) {
    // 1. Observa la lista de tareas
    val tasks by viewModel.tasks.collectAsState()

    // 2. Carga las tareas al entrar
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
            Text("Eliminar Tarea", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))
        }

        items(tasks) { task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                // 3. Un Row para alinear el texto y el botón
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Columna para el texto
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Estado: ${task.status}", style = MaterialTheme.typography.bodyMedium)
                    }

                    // 4. Botón de eliminar
                    IconButton(onClick = {
                        viewModel.deleteTask(task) // 5. Llama al ViewModel
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar Tarea", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}