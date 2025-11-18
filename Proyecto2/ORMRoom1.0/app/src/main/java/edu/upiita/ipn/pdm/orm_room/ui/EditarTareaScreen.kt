package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskDto
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable
fun EditarTareaScreen(
    viewModel: TaskViewModel,
    paddingValues: PaddingValues,
    onNavigateBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<TaskDto?>(null) }
    var editingName by remember { mutableStateOf("") }
    var editingDescripcion by remember { mutableStateOf("") }
    var editingDeadline by remember { mutableStateOf("") }
    var editingStatus by remember { mutableStateOf("") }

    // 1. Estados para el error de fecha por si la fecha es inválida
    var isDateError by remember { mutableStateOf(false) }
    var dateErrorMessage by remember { mutableStateOf("") }

    val tasks by viewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getTasks()
    }

    if (showDialog && selectedTask != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Editar Tarea") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = editingName,
                        onValueChange = { editingName = it },
                        label = { Text("Nombre") }
                    )
                    OutlinedTextField(
                        value = editingDescripcion,
                        onValueChange = { editingDescripcion = it },
                        label = { Text("Descripción") }
                    )
                    OutlinedTextField(
                        value = editingDeadline,
                        onValueChange = { 
                            editingDeadline = it 
                            isDateError = false
                        },
                        label = { Text("Fecha Límite (YYYY-MM-DD)") },
                        // 2. Muestra el estado de error
                        isError = isDateError,
                        supportingText = { 
                            if (isDateError) {
                                Text(dateErrorMessage, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                    OutlinedTextField(
                        value = editingStatus,
                        onValueChange = { editingStatus = it },
                        label = { Text("Estado") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    // 3. Lógica de validación de fecha
                    var parsedDate: LocalDate? = null
                    try {
                        parsedDate = LocalDate.parse(editingDeadline)
                        if (parsedDate.isBefore(LocalDate.now())) {
                            isDateError = true
                            dateErrorMessage = "La fecha no puede ser anterior a hoy."
                            return@Button
                        }
                    } catch (e: DateTimeParseException) {
                        isDateError = true
                        dateErrorMessage = "Formato de fecha inválido."
                        return@Button
                    }

                    // Si la validación es correcta, procedemos
                    val updatedTask = selectedTask!!.copy(
                        name = editingName,
                        descripcion = editingDescripcion,
                        deadline = editingDeadline,
                        status = editingStatus
                    )
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
                Spacer(modifier = Modifier.height(8.dp))
                Text("Editar Tarea", style = MaterialTheme.typography.headlineSmall)
            }
            Text(
                "Haz clic en una tarea para editarla",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
        }

        items(tasks) { task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        selectedTask = task
                        editingName = task.name
                        editingDescripcion = task.descripcion
                        editingDeadline = task.deadline.take(10)
                        editingStatus = task.status
                        isDateError = false // Resetea el error al abrir el diálogo
                        showDialog = true
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Descripción: ${task.descripcion}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Deadline: ${task.deadline.take(10)}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
