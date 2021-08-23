package ge.nnasaridze.alarmapp.scenes.main.repository

import android.app.Activity
import androidx.room.Room
import ge.nnasaridze.alarmapp.scenes.main.repository.database.MainDatabase

class MainRepositoryImpl(activity: Activity) : MainRepository {
    private val mainDAO = Room.databaseBuilder(
        activity.applicationContext,
        MainDatabase::class.java,
        "main_database"
    ).build().mainDao()


    companion object {
        private lateinit var INSTANCE: MainRepository

        fun getInstance(activity: Activity): MainRepository {
            if (!::INSTANCE.isInitialized)
                INSTANCE = MainRepositoryImpl(activity)
            return INSTANCE
        }
    }

    override suspend fun removeItem(item: MainEntity) {
        mainDAO.remove(item.id)
    }

    override suspend fun upsertItem(item: MainEntity) {
        mainDAO.remove(item.id)
        mainDAO.insert(item)
    }

    override suspend fun getItems(): MutableList<MainEntity> {
        return mainDAO.getItems().toMutableList()
    }
}