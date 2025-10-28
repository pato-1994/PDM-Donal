package edu.upiita.ipn.pdm.orm_room.model

class UserRepository(private val dao: UsuarioDao, private val taskDao: TaskDao) {
    suspend fun insert(user: Usuario) = dao.insertar(user)
    suspend fun getAll() = dao.obtenerTodo()
    suspend fun findByEmail(email: String) = dao.obtenerPorCorreo(email)
    suspend fun findByName(name: String) = dao.getUserByName(name)
    suspend fun findByStudentId(studentId: String) = dao.obtenerPorBoleta(studentId)

    // --- NUEVAS Funciones para Tareas ---
    suspend fun getAllTasks() = taskDao.getAllTasks()
    suspend fun updateTasks(tasks: List<Task>) = taskDao.updateTasks(tasks)
    // (Opcional, pero lo necesitarás para "Agregar Tarea")
    suspend fun insertTask(task: Task) = taskDao.insert(task)
    suspend fun updateTask(task: Task)  = taskDao.update(task)
    suspend fun deleteTask(task: Task) = taskDao.delete(task)



}