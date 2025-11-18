package edu.upiita.ipn.pdm.orm_room.network

import edu.upiita.ipn.pdm.orm_room.network.dto.TaskDto
import edu.upiita.ipn.pdm.orm_room.network.dto.TaskRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.DELETE

interface ApiService {

    @GET("api/tasks")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): List<TaskDto>

    @POST("api/tasks")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body taskRequest: TaskRequest
    ): TaskDto

    @PUT("api/tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: Int,

        @Body taskRequest: TaskRequest
    ): TaskDto

    @DELETE("api/tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") taskId: Int
    ) : Unit
}
