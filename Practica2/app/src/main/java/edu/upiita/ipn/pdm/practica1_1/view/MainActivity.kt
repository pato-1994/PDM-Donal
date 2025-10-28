package edu.upiita.ipn.pdm.practica1_1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.upiita.ipn.pdm.practica1_1.R
import edu.upiita.ipn.pdm.practica1_1.databinding.ActivityMainBinding
import edu.upiita.ipn.pdm.practica1_1.model.UserProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userProvider = UserProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnEntrar.setOnClickListener {
            val correo = binding.editTxtCorreo.text.toString().trim()
            val pass = binding.editTxtPass.text.toString().trim()
            if(!userProvider.validaMail(correo)){
                Toast.makeText(this, "El formato del correo no es valido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!userProvider.validaPass(pass)){
                Toast.makeText(this, "La contraseña no cumple con los requisitos de seguridad", Toast.LENGTH_SHORT).show()
                binding.editTxtPass.text.clear()
                return@setOnClickListener
            }
            val usuarioAutenticado = userProvider.autenticarUsuario(this, correo, pass)
            if (usuarioAutenticado != null) {
                binding.editTxtCorreo.text.clear()
                binding.editTxtPass.text.clear()
                Toast.makeText(this, "Bienvenido ${usuarioAutenticado.nombre}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, WelcomeUser::class.java)
                intent.putExtra("nombre", usuarioAutenticado.nombre)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnRenewPass.setOnClickListener {
            intent = Intent(this, CambiaPass::class.java)
            Toast.makeText(this,"Vamos alla", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}