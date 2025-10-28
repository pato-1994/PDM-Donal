package edu.upiita.ipn.pdm.practica1_1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.upiita.ipn.pdm.practica1_1.R
import edu.upiita.ipn.pdm.practica1_1.databinding.ActivityCambiaPassBinding
import edu.upiita.ipn.pdm.practica1_1.model.UserProvider

class CambiaPass : AppCompatActivity() {
    private lateinit var binding: ActivityCambiaPassBinding
    val userProvider = UserProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiaPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnValidaCorreo.setOnClickListener {
            val correo = binding.editTxtCorrRecuperar.text.toString().trim()

            // 1. Validar que el campo no esté vacío
            if (correo.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un correo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Usar la nueva función para buscar al usuario por su correo
            val usuarioEncontrado = userProvider.findUserByEmail(this, correo)

            // 3. Comprobar el resultado de la búsqueda
            if (usuarioEncontrado != null) {
                // ÉXITO: El correo fue encontrado en el JSON
                Toast.makeText(this, "Correo encontrado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, SeguridadUsuario::class.java)

                // - El correo del usuario (para saber a quién cambiarle la contraseña después)
                // - La pregunta de seguridad
                intent.putExtra("user_mail", usuarioEncontrado.correo)
                intent.putExtra("user_pregunta", usuarioEncontrado.pregunta)
                intent.putExtra("user_respuesta", usuarioEncontrado.respuesta) //Pasamos también la respuesta para poder validarla en la siguiente pantalla
                intent.putExtra("user_name", usuarioEncontrado.nombre)
                startActivity(intent)

            } else {
                // FALLO: El correo no existe en la base de datos (JSON)
                Toast.makeText(
                    this,
                    "El correo electrónico no se encuentra registrado",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}