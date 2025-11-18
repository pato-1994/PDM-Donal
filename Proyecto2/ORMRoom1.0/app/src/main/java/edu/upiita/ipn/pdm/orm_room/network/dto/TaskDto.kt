package edu.upiita.ipn.pdm.orm_room.network.dto

data class TaskDto(
    val id: Int? = null,
    val name: String,
    val descripcion: String,
    val status: String,
    val startdate: String,
    val deadline: String
)
