package edu.upiita.ipn.pdm.practica1.model
//Interactuar con el entorno

//mensajes de registro
import android.util.Log
/* Lectura del archivo JSON */
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import android.content.Context
import android.widget.Toast

class UserProvider {
    //Funcion para validar Expresiones regulares
    internal fun validaMail(correo: String): Boolean {

        val asiVaMail = "[a-zA-Z0-9._-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}"
        val regEx = Regex(asiVaMail)

        if (regEx.matches(correo)) {
            Log.d("Valido", "Mail Escrito Correctamente")
            return true
        } else {
            Log.d("Invalido", "Mail Escrito Incorrectamente")
            return false
        }
    }

    //Funcion para encontrar Mayuscula
    private fun hayMayus(pass: String): Boolean {
        if (pass.any { it.isUpperCase() }) {
            return true
        } else return false
    }

    //Funcion para encontrar numero
    private fun hayNum(pass: String): Boolean {
        if (pass.any { it.isDigit() }) {
            return true
        } else return false
    }

    //Fun para validar si tiene (_, . , #, $, ?).
    private fun validaEspecial(pass: String): Boolean {
        val cEspecial = "_.#$?-@,"
        if (cEspecial.any() { it in pass }) {
            return true
        } else return false
    }

    //Funcion para validar contraseña partir de otras funciones
    internal fun validaPass(pass: String): Boolean {
        if (pass.length < 8) {
            Log.d("Invalido", "Contraseña corta como tu")
            return false
        }
        if (!hayMayus(pass)) {
            Log.d("Invalido", "Contraseña sin Mayuscula")
            return false
        }
        if (!hayNum(pass)) {
            Log.d("Invalido", "Contraseña sin Numero")
            return false
        }
        if (!validaEspecial(pass)) {
            Log.d("Invalido", "Contraseña sin Caracter Especial")
            return false
        }
        Log.d("Valido", "Contraseña Correcta")

        return true

    }

    //Funcion para leer el archivo json completo
    internal fun readUserData(context: Context): List<UserModel> {
        val inputStream = context.assets.open("users.json")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        // Define el tipo de dato: una Lista de UserModel
        val userListType = object : TypeToken<List<UserModel>>() {}.type
        // Convierte el JSON en una lista de objetos UserModel
        return gson.fromJson(reader, userListType)
    }

    //Funcion para validar pregunta
    internal fun validaEMailFRecup(correo: String, context: Context): String {
        val userList = readUserData(context)
        val pregunta : String
        for (user in userList) {
            if (user.correo == correo) {
                return user.pregunta
            }
        }
        Toast.makeText(context, "Correo no encontrado", Toast.LENGTH_SHORT).show()

    }

}