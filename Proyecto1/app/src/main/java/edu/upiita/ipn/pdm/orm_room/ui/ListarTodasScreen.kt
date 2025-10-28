package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel

@Composable
fun UserMostrarTTareaScreen(
    viewModel: UserViewModel,
    paddingValues: PaddingValues
) {
    // 1. Observa la lista de tareas del ViewModel
    val tasks by viewModel.tasks.collectAsState()

    // 2. Llama a loadTasks() solo una vez cuando la pantalla aparece
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
            Text("Todas las Tareas", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))
        }

        // 3. Recorre la lista de tareas y crea un item por cada una
        items(tasks) { task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Estado: ${task.status}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "ID Plan: ${task.planneID}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}