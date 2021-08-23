package ge.nnasaridze.alarmapp.scenes.main.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.nnasaridze.alarmapp.scenes.main.repository.MainEntity

@Database(entities = [MainEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDAO
}
