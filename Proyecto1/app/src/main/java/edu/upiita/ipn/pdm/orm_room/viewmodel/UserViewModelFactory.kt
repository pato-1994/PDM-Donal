package edu.upiita.ipn.pdm.orm_room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.upiita.ipn.pdm.orm_room.model.UserRepository

// 1. La clase Factory recibe las dependencias que el ViewModel necesita (el repositorio).
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    // 2. El sistema llamará a este método "create".
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 3. Verificamos que nos están pidiendo un UserViewModel.
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            // 4. Si es así, lo construimos con el repositorio que tenemos y lo devolvemos.
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        // 5. Si nos piden cualquier otro tipo de ViewModel, lanzamos un error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
