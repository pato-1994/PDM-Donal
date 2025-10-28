package edu.upiita.ipn.pdm.orm_room.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)
    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>
    @Update
    suspend fun updateTasks(tasks: List<Task>) // Permite actualizar una lista de tareas
    //funciones para eliminar tareas y actualizar
    @Update
    suspend fun update(task: Task)
    @Delete
    suspend fun delete(task: Task)
}