package edu.upiita.ipn.pdm.orm_room.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.upiita.ipn.pdm.orm_room.model.Repository
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskDto
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val repository = Repository()

    private val _tasks = MutableStateFlow<List<TaskDto>>(emptyList())
    val tasks: StateFlow<List<TaskDto>> = _tasks

    fun getTasks() {
        viewModelScope.launch {
            try {
                val result = repository.getTasks()
                _tasks.value = result
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al obtener tareas", e)
            }
        }
    }

    fun addTask(taskRequest: TaskRequest) {
        viewModelScope.launch {
            try {
                repository.createTask(taskRequest)
                // Refrescar la lista después de añadir
                getTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al añadir tarea", e)
            }
        }
    }

    fun updateTask(task: TaskDto) {
        viewModelScope.launch {
            try {
                val taskId = task.id
                if (taskId == null){
                    Log.e("TaskViewModel", "Error al actualizar tarea: ID nulo")
                    return@launch
                }
                val taskRequest = TaskRequest(
                    name = task.name,
                    descripcion = task.descripcion,
                    deadline = task.deadline,
                    status = task.status
                )
                Log.d("UpdatecCHeck","Enviando tarea con ID: $taskId. datos: $taskRequest")
                repository.updateTask(taskId , taskRequest)
                getTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar tarea", e)
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteTask(taskId)
                getTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al eliminar tarea", e)
            }
        }
    }
}