package edu.upiita.ipn.pdm.orm_room.network.dto

data class TaskRequest(
    val name: String,
    val descripcion: String,
    val deadline: String,
    val status: String?=null
)
