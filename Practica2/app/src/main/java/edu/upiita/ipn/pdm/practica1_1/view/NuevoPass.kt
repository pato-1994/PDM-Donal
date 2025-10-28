package edu.upiita.ipn.pdm.practica1_1.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.upiita.ipn.pdm.practica1_1.R
import edu.upiita.ipn.pdm.practica1_1.databinding.ActivityNuevoPassBinding
import edu.upiita.ipn.pdm.practica1_1.model.UserProvider

class NuevoPass : AppCompatActivity() {
    private lateinit var binding: ActivityNuevoPassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userProvider = UserProvider()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) {v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userMail = intent.getStringExtra("user_mail")
        val userName = intent.getStringExtra("user_name")
        if (userMail==null){
            Toast.makeText(this, "Error: No se encontro el correo del usuario", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        binding.txtViuNameNuevo.text = userName
        binding.btnCambiarNueva.setOnClickListener {
            val newPass = binding.txtPassNueva.text.toString()
            val confrmaPass = binding.txtPassNewConfirma.text.toString()
            if(newPass.isEmpty() || confrmaPass.isEmpty()){
                Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(newPass != confrmaPass) {
                Toast.makeText(this, "Las Contrasenas no coinciden", Toast.LENGTH_SHORT).show()
                binding.txtPassNewConfirma.text.clear()
                binding.txtPassNueva.text.clear()
                return@setOnClickListener
            }
            val updateLlisto = userProvider.updateUserPassword(this, userMail, newPass)
            if (updateLlisto){
                Toast.makeText(this,"Contrasena actualizada exitosamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "No se pudo actualizar tu contrasena", Toast.LENGTH_SHORT).show()
            }
        }
    }
}