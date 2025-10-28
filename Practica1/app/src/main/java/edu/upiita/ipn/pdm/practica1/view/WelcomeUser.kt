package edu.upiita.ipn.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import edu.upiita.ipn.pdm.practica1.R
import kotlin.getValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import edu.upiita.ipn.pdm.practica1.databinding.ActivityWelcomeUserBinding

import edu.upiita.ipn.pdm.practica1.model.UserProvider

class WelcomeUser : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeUserBinding

    //private val userViewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityWelcomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.barrita)

        val correo = intent.getStringExtra("deEmail")
        //val pass = intent.getStringExtra("dePass")
        val userProvider = UserProvider()
        val muestraUser = userProvider.readUserData(this)
        val buscaUser = muestraUser.find { it.correo == correo }

        if (buscaUser != null) {
            //buscar el usuario
            val nombreUser = buscaUser.nombre
            binding.txtViewWelcome.setText("Bienvenido $nombreUser")
            binding.txtPrueba.setText("Bienvenido, tu correo es $correo")
        } else {
            binding.txtViewWelcome.text = "¡Bienvenido!"
            binding.txtPrueba.setText("No se encontraron los datos del usuario.")
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menubar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opc_change_passwd -> {
                Toast.makeText(this, "Cambiar contraseña", Toast.LENGTH_SHORT).show()
                // Aquí iría la lógica para cambiar la contraseña
                true // Indica que el evento ha sido manejado
            }

            R.id.opc_logout -> {
                Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show()
                // Aquí iría la lógica para cerrar sesión
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true // Indica que el evento ha sido manejado

            }

            else -> super.onOptionsItemSelected(item)

        }
    }
}

class RecuperaPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recupera_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}