package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(viewModel: TaskViewModel) { // <-- ¡CORREGIDO!

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Una versión limpia del menú
            MenuScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Gestión De Tareas (API)") }, // Título actualizado
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Pasamos el viewModel al Navigator
            Navigator(
                viewModel = viewModel,
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}
