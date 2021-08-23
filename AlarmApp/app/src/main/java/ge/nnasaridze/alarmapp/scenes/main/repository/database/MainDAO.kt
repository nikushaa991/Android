package ge.nnasaridze.alarmapp.scenes.main.repository.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.nnasaridze.alarmapp.scenes.main.repository.MainEntity

@Dao
interface MainDAO {

    @Query("SELECT * FROM MainEntity")
    fun getItems(): List<MainEntity>

    @Insert
    fun insert(item: MainEntity)

    @Query("DELETE FROM MainEntity WHERE id = :id")
    fun remove(id: Int)
}
