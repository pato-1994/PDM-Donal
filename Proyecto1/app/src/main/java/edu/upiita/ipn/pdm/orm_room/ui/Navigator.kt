package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.PaddingValues // 1. ¡IMPORTA ESTO!
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel
// ... (Tus otras importaciones de pantallas)

@Composable
fun Navigator(
    viewModel: UserViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues // 2. ¡AÑADE ESTE PARÁMETRO!
) {

    NavHost(navController = navController, startDestination = "inicio") {

        composable("inicio") {
            // 3. Pasa los paddings a tu pantalla de inicio
            UserListScreen(
                viewModel = viewModel,
                paddingValues = paddingValues // <-- AÑADE ESTO
            ) { }
        }

        composable ("Agregar Tarea") {
            UserAgregarTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            ) { }
            /* UserAgregarTareaScreen(viewModel, paddingValues) {} */ // <-- AÑADE PADDINGS AQUÍ TAMBIÉN
        }

        composable("Editar Tarea") {
            // 4. Ahora esto funcionará, porque "paddingValues" sí existe
            UserEditarTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 5. ¡RECUERDA AÑADIR paddingValues A TODAS TUS PANTALLAS!
        composable("Eliminar Tarea") {
            UserEliminarTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues

            )
            /* UserEliminarTareaScreen(viewModel, paddingValues) {} */
        }
        composable("Listar todas las tareas") {
            UserMostrarTTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
            /* UserMostrarTTareaScreen(viewModel, paddingValues) {} */
        }
        composable("Marcar Estado de Tarea") {
            UserMostrarEstadoScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
            /* UserMostrarEstadoScreen(viewModel, paddingValues) {} */
        }
    }
}