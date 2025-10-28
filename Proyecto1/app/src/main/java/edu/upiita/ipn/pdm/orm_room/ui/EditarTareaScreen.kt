package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.model.Task
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel

@Composable
fun UserEditarTareaScreen(
    viewModel: UserViewModel,
    paddingValues: PaddingValues,
    onNavigateBack: () -> Unit // Usaremos esta para el botón de regreso
) {
    // 1. Estados para manejar el diálogo
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var editingTaskName by remember { mutableStateOf("") }

    // 2. Observa y carga las tareas
    val tasks by viewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    // 3. Si showDialog es true, muestra el diálogo
    if (showDialog && selectedTask != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Editar Tarea") },
            text = {
                // 4. Campo de texto dentro del diálogo
                OutlinedTextField(
                    value = editingTaskName,
                    onValueChange = { editingTaskName = it },
                    label = { Text("Nuevo nombre") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    // 5. Al confirmar, actualiza la tarea
                    val updatedTask = selectedTask!!.copy(name = editingTaskName)
                    viewModel.updateTask(updatedTask)
                    showDialog = false
                    selectedTask = null
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // --- Pantalla principal (la lista) ---
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                }
                Text("Editar Tarea", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(start = 8.dp))
            }
            Text("Haz clic en una tarea para editar su nombre", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 8.dp, start = 8.dp))

        }

        items(tasks) { task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { // 6. Hacemos que la tarjeta sea clickeable
                        selectedTask = task
                        editingTaskName = task.name // Pre-llena el TextField
                        showDialog = true // Muestra el diálogo
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Estado: ${task.status}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}