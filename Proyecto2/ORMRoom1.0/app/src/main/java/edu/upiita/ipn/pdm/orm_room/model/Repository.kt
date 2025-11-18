package edu.upiita.ipn.pdm.orm_room.model

import edu.upiita.ipn.pdm.orm_room.network.RetrofitClient
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskDto
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskRequest

/**
 * Repositorio que gestiona las operaciones de datos de la API remota.
 */
class Repository {

    // Token de autenticación
    private val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJvb3QiLCJpYXQiOjE3NjMxNjg2NzUsImV4cCI6MTc5NDcyNjI3NX0.u_nVRCc_qFI885bHDDPp1NOW3imtF-gT4bAT3fHEggg"

    /**
     * Obtiene la lista de tareas desde el servidor y bd web.
     */
    suspend fun getTasks(): List<TaskDto> {
        return RetrofitClient.api.getTasks(token)
    }

    /**
     * Crea una nueva tarea en el servidor.
     */
    suspend fun createTask(taskRequest: TaskRequest): TaskDto {
        return RetrofitClient.api.createTask(token, taskRequest)
    }

    suspend fun updateTask(taskId: Int, taskRequest: TaskRequest): TaskDto {
        return RetrofitClient.api.updateTask(token, taskId, taskRequest)
    }

    suspend fun deleteTask(taskId: Int) {
        RetrofitClient.api.deleteTask(token, taskId)
    }
}
