package edu.upiita.ipn.pdm.orm_room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.upiita.ipn.pdm.orm_room.model.Task
import edu.upiita.ipn.pdm.orm_room.model.UserRepository
import edu.upiita.ipn.pdm.orm_room.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // --- Flujos y Funciones para Usuario ---

    private val _users = MutableStateFlow<List<Usuario>>(emptyList())
    val users: StateFlow<List<Usuario>> get() = _users

    private val _selectedUser = MutableStateFlow<Usuario?>(null)
    val selectedUser: StateFlow<Usuario?> get() = _selectedUser

    fun loadUsers() {
        viewModelScope.launch {
            _users.value = repository.getAll()
        }
    }

    fun addUser(user: Usuario) {
        viewModelScope.launch {
            repository.insert(user)
            loadUsers()
        }
    }

    fun findUserByEmail(email: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByEmail(email)
        }
    }

    fun findUserByName(name: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByName(name)
        }
    }

    fun findUserByStudentId(studentId: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByStudentId(studentId)
        }
    }

    // --- NUEVOS Flujos y Funciones para Tareas ---

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAllTasks()
        }
    }

    fun updateAllTasks(tasks: List<Task>) {
        viewModelScope.launch {
            repository.updateTasks(tasks)
            // Opcional: recargar la lista después de actualizar
            loadTasks()
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
            loadTasks() // Recargar la lista despues de añadir
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks() // Recargar la lista después de actulaizar
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            loadTasks() // Recargar la lista después de eliminar
        }
    }
}