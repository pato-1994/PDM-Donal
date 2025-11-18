package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel

@Composable
fun ListarTodasScreen(
    viewModel: TaskViewModel,
    paddingValues: PaddingValues
) {
    // 1. Observa la lista de tareas desde el ViewModel correcto
    val tasks by viewModel.tasks.collectAsState()

    // 2. Llama a getTasks() para cargar las tareas desde la API
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
            Text("Todas las Tareas", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))
        }

        // 3. Recorre la lista de tareas (TaskDto)
        items(tasks) { task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // 4. Muestra los campos del modelo correcto (TaskDto)
                    Text(text = "ID: ${task.id}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Fecha Inicio: ${task.startdate}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Descripción: ${task.descripcion}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Estado: ${task.status}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Fecha Finalización: ${task.deadline}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
