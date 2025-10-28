package edu.upiita.ipn.pdm.practica1_1.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.upiita.ipn.pdm.practica1_1.R
import edu.upiita.ipn.pdm.practica1_1.databinding.ActivityWelcomeUserBinding

class WelcomeUser : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeUserBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.barrita)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) {v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombreusuario = intent.getStringExtra("nombre")
        binding.txtViewUserBienvenido.text = "Bienvenido $nombreusuario"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menubar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opc_change_passwd -> {
                intent = Intent(this, CambiaPass::class.java)
                startActivity(intent)

                true
            }
            R.id.opc_logout -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}