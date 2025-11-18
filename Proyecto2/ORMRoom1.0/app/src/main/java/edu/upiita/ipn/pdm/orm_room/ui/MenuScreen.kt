package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Un menú de navegación simple y reutilizable
@Composable
fun MenuScreen(onNavigate: (String) -> Unit) {

    // Las rutas que definimos en el Navigator
    val routes = listOf(
        "inicio",
        "Agregar Tarea",
        "Editar Tarea",
        "Eliminar Tarea",
        "Listar todas las tareas",
        //"Marcar Estado de Tarea"
    )

    ModalDrawerSheet {
        Column(modifier = Modifier.padding(16.dp)) {
            routes.forEach { route ->
                NavigationDrawerItem(
                    label = { Text(route.replaceFirstChar { it.uppercase() }) }, // Pone la primera letra en mayúscula
                    selected = false,
                    onClick = { onNavigate(route) }
                )
            }
        }
    }
}
