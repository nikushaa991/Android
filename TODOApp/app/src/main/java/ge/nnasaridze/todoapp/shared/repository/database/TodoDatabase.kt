package ge.nnasaridze.todoapp.shared.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ge.nnasaridze.todoapp.shared.repository.StringListConverter
import ge.nnasaridze.todoapp.shared.repository.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDAO
}
