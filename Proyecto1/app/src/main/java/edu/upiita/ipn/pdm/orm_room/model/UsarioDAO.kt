package edu.upiita.ipn.pdm.orm_room.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(usuario: Usuario)
    //consultas//? quiere decir que puede ser null
    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun obtenerPorCorreo(correo: String): Usuario?
    @Query("SELECT * FROM usuarios WHERE boleta = :boleta")
    suspend fun obtenerPorBoleta(boleta: String): Usuario?
    @Query("SELECT * FROM usuarios")
    suspend fun obtenerTodo(): List<Usuario>
    @Query("SELECT * FROM usuarios WHERE nombre = :nombre")
    suspend fun getUserByName(nombre: String): Usuario?

}
