// En tu archivo RecuperaPass.kt
//package edu.upiita.ipn.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import edu.upiita.ipn.pdm.practica1.databinding.ActivityRecuperaPassBinding
import edu.upiita.ipn.pdm.practica1.model.UserProvider
import edu.upiita.ipn.pdm.practica1.view.RespondeActivity

class RecuperaPass : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperaPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperaPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnValidaEMailFRecup.setOnClickListener {
            val correo = binding.editTxtCorreo.text.toString()
            val userProvider = UserProvider()
           val preg = userProvider.validaEMailFRecup(correo, this)
            
            intent = Intent(this, RespondeActivity::class.java)
            startActivity(intent)
        }

    }


}