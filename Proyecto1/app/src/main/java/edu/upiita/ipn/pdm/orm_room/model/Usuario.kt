package edu.upiita.ipn.pdm.orm_room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val boleta: String,
    val nombre: String,
    val correo: String,
    val carrera: String,
    val contraseña: String
)