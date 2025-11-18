package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel

@Composable
fun Navigator(
    viewModel: TaskViewModel, // 1. ViewModel correcto
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = "inicio") {

        //  Rutas actualizadas a las nuevas pantallas
        composable("inicio") {
            MainScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }

        composable("Agregar Tarea") {
            AgregarTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onTaskAdded = { navController.popBackStack() } // Regresa al terminar
            )
        }

        composable("Editar Tarea") {
            EditarTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onNavigateBack = { navController.popBackStack() } // Regresa al terminar
            )
        }

        composable("Eliminar Tarea") {
            EliminarTareaScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }

        composable("Listar todas las tareas") {
            ListarTodasScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }

        composable("Marcar Estado de Tarea") {
            MarcarEstadoTaskScreen(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
    }
}
