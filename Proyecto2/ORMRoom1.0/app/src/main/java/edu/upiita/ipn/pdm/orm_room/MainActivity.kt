package edu.upiita.ipn.pdm.orm_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.upiita.ipn.pdm.orm_room.ui.screen.TaskScreen
import edu.upiita.ipn.pdm.orm_room.ui.AppScaffold
import edu.upiita.ipn.pdm.orm_room.ui.theme.ORMRoomTheme
import edu.upiita.ipn.pdm.orm_room.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ORMRoomTheme {
                // Instancia el ViewModel correctamente dentro del contexto de Compose
                val viewModel: TaskViewModel = viewModel()
                
                // Llama a la pantalla principal de tareas, que ahora obtiene los datos de la API
                TaskScreen(viewModel = viewModel)
                AppScaffold(viewModel = viewModel)
            }
        }
    }
}
