package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upiita.ipn.pdm.orm_room.model.Task
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel

@Composable
fun UserAgregarTareaScreen(
    viewModel: UserViewModel,
    paddingValues: PaddingValues,
    onTaskAdded: () -> Unit // Lambda para notificar que se agregó
) {
    // 1. Estados para guardar lo que el usuario escribe
    var taskName by remember { mutableStateOf("") }
    var planneId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Agregar Nueva Tarea", style = MaterialTheme.typography.headlineSmall)

        // 2. Campo de texto para el nombre
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Nombre de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        // 3. Campo de texto para el PlanneID
        OutlinedTextField(
            value = planneId,
            onValueChange = { planneId = it },
            label = { Text("ID del Planificador") },
            modifier = Modifier.fillMaxWidth()
        )

        // 4. Botón para guardar
        Button(
            onClick = {
                if (taskName.isNotBlank() && planneId.isNotBlank()) {
                    // 5. Creamos la tarea y la mandamos al ViewModel
                    val newTask = Task(
                        name = taskName,
                        planneID = planneId,
                        status = "Pendiente" // Por defecto al crearla
                    )
                    viewModel.addTask(newTask)

                    // 6. Limpiamos los campos
                    taskName = ""
                    planneId = ""
                    // 7. (Opcional) Llama a la lambda
                    onTaskAdded()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Tarea")
        }
    }
}