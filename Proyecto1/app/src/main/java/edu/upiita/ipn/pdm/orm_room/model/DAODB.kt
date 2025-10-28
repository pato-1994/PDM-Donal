package edu.upiita.ipn.pdm.orm_room.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class, Task::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    abstract fun taskDao(): TaskDao //funcion abstracta para el nuevo DAO de tareas

}