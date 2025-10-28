package edu.upiita.ipn.pdm.orm_room.ui

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import edu.upiita.ipn.pdm.orm_room.viewmodel.UserViewModel
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserListScreen(
viewModel: UserViewModel,
paddingValues: PaddingValues,
onSearch: () -> Unit
) {
    val tasks = viewModel.tasks.collectAsState()//observa la lista de Tareas
    LaunchedEffect(Unit) { viewModel.loadTasks() } //carga las tareas

    val users = viewModel.users.collectAsState()//observa la lista de usuarios
    LaunchedEffect(Unit) { viewModel.loadUsers() } //carga los usuarios

    //para los usuarios
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        items(users.value) { user ->
            Text(user.nombre)
        }
    }

    //para las tareas
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ){
        //recorre la lista de tareas
        items(tasks.value){ tasks ->
            //muestra los datos de la tarea (se puede personalizar)
            Column(modifier = Modifier.padding(16.dp)){
                Text(tasks.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Estado: ${tasks.status}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}