package ge.nnasaridze.todoapp.shared.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.nnasaridze.todoapp.shared.repository.TodoEntity

@Dao
interface TodoDAO {

    @Query("SELECT * FROM TodoEntity ORDER BY lastEdited DESC")
    fun getItems(): List<TodoEntity>

    @Insert
    fun insert(item: TodoEntity)

    @Query("DELETE FROM TodoEntity WHERE id = :id")
    fun remove(id: Int)
}
