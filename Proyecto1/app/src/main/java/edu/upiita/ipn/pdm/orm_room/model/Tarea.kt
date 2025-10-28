package edu.upiita.ipn.pdm.orm_room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task") //Nombre de la tabla en la BD{
data class Task( //representa la tabla en la app como objeto
    @PrimaryKey(autoGenerate = true) /*llave primaria*/ val id: Int = 0,
    val name: String,
    val planneID: String,
    val status: String)
