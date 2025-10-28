package edu.upiita.ipn.pdm.practica1_1.model
import com.google.gson.annotations.SerializedName
//Con parentesis para entender que es un constructor primario
data class UserModel (
    @SerializedName("name") val nombre: String,
    @SerializedName("email") val correo: String,
    @SerializedName("password") val pass: String,
    @SerializedName("preguntaSecre") val pregunta: String,
    @SerializedName("respuestaSecre") val respuesta: String

)