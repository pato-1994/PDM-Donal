package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserMenuScreen(onNavigate: (String) -> Unit) {

    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text("Agregar Tarea") },
            selected = false,
            onClick = {onNavigate("Agregar Tarea")}
        )
        NavigationDrawerItem(
            label = { Text("Editar Tarea")},
            selected = false,
            onClick = {onNavigate("Editar Tarea")}
        )
        NavigationDrawerItem(
            label = { Text("Eliminar Tarea")},
            selected = false,
            onClick = { onNavigate("Eliminar Tarea") }
        )
        NavigationDrawerItem(
            label = { Text("Listar todas las tareas")},
            selected = false,
            onClick = {onNavigate("Listar todas las tareas")}
        )
        NavigationDrawerItem(
            label = { Text("Marcar Estado de Tarea")},
            selected = false,
            onClick = {onNavigate("Marcar Estado de Tarea")}
        )
    }
}