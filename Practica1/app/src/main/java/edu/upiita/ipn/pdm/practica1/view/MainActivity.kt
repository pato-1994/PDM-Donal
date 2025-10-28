package edu.upiita.ipn.pdm.practica1.view

// En tu archivo MainActivity.kt
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import edu.upiita.ipn.pdm.practica1.databinding.ActivityMainBinding
import edu.upiita.ipn.pdm.practica1.viewmodel.LoginViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var loginViewModel: LoginViewModel // Declara el ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate( savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el ViewModel
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setupObservers() // Configura los observadores

        binding.btnStartSsion.setOnClickListener {
            val correo = binding.inTxtEmail.text.toString().trim()
            val pass = binding.inTxtPass.text.toString().trim()

            // La Activity solo le dice al ViewModel "intenta hacer login"
            loginViewModel.login(correo, pass)
        }

        binding.btnOlvidePass.setOnClickListener {
            val intent = Intent(this, RecuperaPass::class.java) // Asegúrate que sea RecuperaPass
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        // Observador para la navegación
        loginViewModel.navigateToWelcome.observe(this) { correo ->
            correo?.let {
                val intent = Intent(this, WelcomeUser::class.java)
                intent.putExtra("deEmail", it) // Pasamos el correo a la siguiente pantalla
                startActivity(intent)
                binding.inTxtEmail.text.clear()
                binding.inTxtPass.text.clear()

                // Reseteamos el evento para no volver a navegar si se rota la pantalla
                loginViewModel.onNavigationComplete()
            }
        }

        // Observador para mensajes de error
        loginViewModel.errorMessage.observe(this) { message ->
            // Muestra el error en un Toast o en tu TextView
            binding.txtViewMsg.text = message
            // Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}