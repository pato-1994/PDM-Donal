package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskRequest
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable
fun AgregarTareaScreen(
    viewModel: TaskViewModel,
    paddingValues: PaddingValues,
    onTaskAdded: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    var isDateError by remember { mutableStateOf(false) }
    var dateErrorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Agregar Nueva Tarea", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deadline,
            onValueChange = { 
                deadline = it
                isDateError = false 
            },
            label = { Text("Fecha Límite (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            isError = isDateError,
            supportingText = { 
                if (isDateError) {
                    Text(dateErrorMessage, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Button(
            onClick = {
                var parsedDate: LocalDate? = null
                try {
                    parsedDate = LocalDate.parse(deadline)
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

                if (name.isNotBlank() && descripcion.isNotBlank()) {
                    isDateError = false
                    
                    // --- ¡LA CORRECCIÓN DEFINITIVA! ---
                    // 1. Se crea el objeto que la función necesita.
                    val taskRequest = TaskRequest(
                        name = name,
                        descripcion = descripcion,
                        deadline = deadline
                    )
                    // 2. Se llama a la función con el objeto correcto.
                    viewModel.addTask(taskRequest)

                    onTaskAdded()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Tarea")
        }
    }
}
