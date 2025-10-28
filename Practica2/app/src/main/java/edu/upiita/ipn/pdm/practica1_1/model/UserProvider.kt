package edu.upiita.ipn.pdm.practica1_1.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.semantics.password
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import com.google.gson.GsonBuilder // Para poder usar GsonBuilder
import java.io.File              // Para poder usar la clase File

class UserProvider {
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
    /*
    internal fun readUserData(context: Context): List<UserModel> {
        val inputStream = context.assets.open("users.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        Log.d("UserProvider_Debug", "Conteniudo de users.json: $jsonString")
        val gson = Gson()
        // Define el tipo de dato: una Lista de UserModel
        val listUserType = object : TypeToken<List<UserModel>>() {}.type
        val users: List<UserModel> = gson.fromJson(jsonString, listUserType)

        Log.d("UserProvide_Debug", "Parseo completado. Numero de Usuarios encontrados ${users.size}")
        if (users.isNotEmpty()){
            Log.d("UserProvider_Debug", "Ejemplo del primer usuario: ${users[0]}")
        }
        return users
    }*/
    //Validar el correo y mandar pregunta
    //No terminada
    internal fun validaEMailFRecup(correo: String, context: Context): String {
        val userList = readUserData(context)
        val pregunta : String
        for (user in userList) {
            if (user.correo == correo) {
                return user.pregunta
            }
        }
        Toast.makeText(context, "Correo no encontrado", Toast.LENGTH_SHORT).show()
        return ""
    }
    internal fun autenticarUsuario(context: Context, correo: String, pass: String): UserModel? {
        // 1. Leemos todos los usuarios del archivo JSON
        val listaDeUsuarios = readUserData(context)

        // Si usuarioEncontrado no es null, significa que la autenticación fue exitosa.
        return listaDeUsuarios.find { it.correo.equals(correo, ignoreCase = true) && it.pass.equals(pass, ignoreCase = true) }
    }
    // Funcion Busca usuario solo por correo
    internal fun findUserByEmail(context: Context, correo: String): UserModel? {
        val listaDeUsuarios = readUserData(context)
        // Usamos .find para buscar el primer usuario que coincida con el correo (ignorando mayúsculas/minúsculas).
        return listaDeUsuarios.find { it.correo.equals(correo, ignoreCase = true) }
    }
    // En UserProvider.kt, después de tu función findUserByEmail

    /**
     * Escribe la lista completa de usuarios de vuelta al archivo users.json en el almacenamiento INTERNO.* @return true si la escritura fue exitosa, false en caso de error.
     */
    internal fun saveUserData(context: Context, users: List<UserModel>): Boolean {
        try {
            // Usamos GsonBuilder para un formato de JSON más legible (pretty printing)
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(users)

            // Obtenemos la ruta al archivo en el almacenamiento interno de la app (no en assets).
            val file = File(context.filesDir, "users.json")

            // Escribimos (o sobreescribimos) el archivo con la nueva lista de usuarios.
            file.writeText(jsonString)

            Log.d("UserProvider", "Datos guardados exitosamente en ${file.absolutePath}")
            return true

        } catch (e: Exception) {
            Log.e("UserProvider", "ERROR al guardar los datos en users.json", e)
            return false
        }
    }

    /**
     * Orquesta el proceso de actualizar la contraseña de un usuario.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    internal fun updateUserPassword(context: Context, email: String, newPass: String): Boolean {
        // 1. Leemos la lista actual de usuarios (asegúrate de que readUserData lea desde el almacenamiento interno si existe).
        val userList = readUserData(context).toMutableList() // La convertimos a lista mutable para poder modificarla.

        // 2. Encontramos el índice del usuario que queremos actualizar.
        val userIndex = userList.indexOfFirst { it.correo.equals(email, ignoreCase = true) }

        // 3. Si encontramos al usuario (el índice no es -1)...
        if (userIndex != -1) {
            val oldUser = userList[userIndex]
            // Creamos una copia del usuario con la contraseña nueva, manteniendo los demás datos.
            val updatedUser = oldUser.copy(pass = newPass)
            // Reemplazamos el usuario antiguo por el nuevo en la lista.
            userList[userIndex] = updatedUser

            // 4. Guardamos la lista completa y actualizada de vuelta en el archivo.
            return saveUserData(context, userList)
        }

        // Si no se encontró el usuario, la actualización falla.
        return false
    }
    //Funcion para leer el archivo json completo
    internal fun readUserData(context: Context): List<UserModel> {
        val internalFile = File(context.filesDir, "users.json")
        val gson = Gson()
        val listUserType = object : TypeToken<List<UserModel>>() {}.type

        try {
            // PRIMERO: Intentar leer desde el almacenamiento interno (donde se guardan los cambios).
            if (internalFile.exists()) {
                Log.d("UserProvider_Debug", "Leyendo desde archivo interno: ${internalFile.path}")
                val jsonString = internalFile.readText()
                return gson.fromJson(jsonString, listUserType)
            } else {
                // SI NO EXISTE: Leer desde 'assets' (la primera vez que corre la app).
                Log.d("UserProvider_Debug", "Archivo interno no encontrado. Leyendo desde assets.")
                val inputStream = context.assets.open("users.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val users: List<UserModel> = gson.fromJson(jsonString, listUserType)

                // Y lo guardamos en el almacenamiento interno para la próxima vez.
                saveUserData(context, users)
                Log.d("UserProvider_Debug", "Archivo copiado de assets a almacenamiento interno.")
                return users
            }
        } catch (e: Exception) {
            Log.e("UserProvider", "ERROR al leer users.json", e)
            return emptyList()
        }
    }


}