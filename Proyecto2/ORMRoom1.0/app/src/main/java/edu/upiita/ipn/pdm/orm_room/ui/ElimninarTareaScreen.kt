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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel

@Composable
fun EliminarTareaScreen(
    viewModel: TaskViewModel,
    paddingValues: PaddingValues
) {
    //  Observa la lista de tareas desde el ViewModel correcto
    val tasks by viewModel.tasks.collectAsState()

    //  Carga las tareas desde la API al entrar
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
            Text("Eliminar Tarea", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))
        }

        items(tasks) { task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                //  Row para alinear el texto y el botón
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Columna para el texto (usando TaskDto)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Estado: ${task.status}", style = MaterialTheme.typography.bodyMedium)
                    }

                    //  Botón de eliminar
                    IconButton(onClick = {
                        // 5. Llama a deleteTask en el ViewModel con el ID de la tarea
                        task.id?.let { viewModel.deleteTask(it) }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar Tarea", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
