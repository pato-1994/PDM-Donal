package edu.upiita.ipn.pdm.practica1.model
import com.google.gson.annotations.SerializedName
//Con parentesis para entender que es un constructor primario
data class UserModel (
    @SerializedName("name") val nombre: String,
    @SerializedName("email") val correo: String,
    @SerializedName("passwd") val pass: String,
    @SerializedName("question") val pregunta: String,
    @SerializedName("answer") val respuesta: String

    )