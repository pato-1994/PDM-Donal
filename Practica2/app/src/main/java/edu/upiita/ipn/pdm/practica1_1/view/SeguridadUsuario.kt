package edu.upiita.ipn.pdm.practica1_1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.upiita.ipn.pdm.practica1_1.R
import edu.upiita.ipn.pdm.practica1_1.databinding.ActivitySeguridadUsuarioBinding



class SeguridadUsuario : AppCompatActivity() {

    private lateinit var binding: ActivitySeguridadUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeguridadUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) {v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            // 1. Recuperar los datos del Intent
            val correoUsuario = intent.getStringExtra("user_mail")
            val preguntaSeguridad = intent.getStringExtra("user_pregunta")
            val respuestaCorrecta = intent.getStringExtra("user_respuesta")
            val nombreUsuario= intent.getStringExtra("user_name")

            // 2. Mostrar la pregunta de seguridad en el TextView
            binding.txtPregunta.text = preguntaSeguridad

            // Lógica del botón para verificar la respuesta
            binding.btnValidResp.setOnClickListener {
                val respuestaUsuario = binding.editTxtRespuesta.text.toString().trim()

                if (respuestaUsuario.equals(respuestaCorrecta, ignoreCase = true)) {
                    // Respuesta Correcta: Navegar a la pantalla final para cambiar la contraseña
                    Toast.makeText(this, "Respuesta correcta", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NuevoPass::class.java)
                    intent.putExtra("user_mail", correoUsuario)
                    intent.putExtra("user_name", nombreUsuario)
                    startActivity(intent)
                } else {
                    // Respuesta Incorrecta
                    Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
