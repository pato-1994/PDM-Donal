package edu.upiita.ipn.pdm.practica1.viewmodel
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.upiita.ipn.pdm.practica1.model.UserProvider

class LoginViewModel : ViewModel() {

    private val userProvider = UserProvider()

    // LiveData para notificar a la Vista que debe navegar
    private val _navigateToWelcome = MutableLiveData<String?>()
    val navigateToWelcome: LiveData<String?> = _navigateToWelcome

    // LiveData para mostrar mensajes de error
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(correo: String, pass: String) {
        // 1. Validar el formato del correo y la contraseña
        if (!userProvider.validaMail(correo)) {
            _errorMessage.value = "El formato del correo es incorrecto."
            return
        }
        if (!userProvider.validaPass(pass)) {
            _errorMessage.value = "La contraseña no cumple los requisitos."
            return
        }

        // 2. Verificar si el usuario existe y las credenciales son correctas
        val users = userProvider.readUserData(getApplication())
        val foundUser = users.find { it.correo == correo && it.pass == pass }

        if (foundUser != null) {
            // Éxito: Notifica a la Activity que navegue, enviando el correo
            _navigateToWelcome.value = correo
        } else {
            // Fracaso: Envía un mensaje de error
            _errorMessage.value = "Correo o contraseña incorrectos."
        }
    }

    private fun getApplication(): Context {
        TODO("Not yet implemented")
    }

    // Función para resetear el evento de navegación
    fun onNavigationComplete() {
        _navigateToWelcome.value = null
    }
}