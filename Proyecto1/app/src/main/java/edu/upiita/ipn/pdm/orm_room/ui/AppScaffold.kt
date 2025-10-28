package edu.upiita.ipn.pdm.orm_room.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(viewModel: UserViewModel) {

    // 1. Creamos el NavController y el estado del Drawer aquí
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope() // Para abrir y cerrar el drawer

    // 2. Este es el contenedor principal del menú lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // 3. Aquí va tu MenuScreen
            UserMenuScreen(
                onNavigate = { route ->
                    // 4. Le decimos al NavController que navegue
                    navController.navigate(route)
                    // 5. Cerramos el menú después de hacer clic
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        // 6. Este es el contenido principal de tu app (la parte visible)
        Scaffold(
            topBar = {
                // 7. Agregamos una barra superior con el botón de "hamburguesa"
                TopAppBar(
                    title = { Text("Menú De Gestión De Tareas") },
                    navigationIcon = {
                        IconButton(onClick = {
                            // 8. El botón abre el menú
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->

            Navigator(
                viewModel = viewModel,
                navController = navController,
                paddingValues = paddingValues
            )
            /*// 9. Dentro del Scaffold, ponemos tu Navigator
            Box(modifier = Modifier.padding(paddingValues)) {
                Navigator(viewModel = viewModel, navController = navController)
            }*/
        }
    }
}