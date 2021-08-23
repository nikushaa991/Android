package ge.nnasaridze.alarmapp.scenes.main.repository

interface MainRepository {
    suspend fun removeItem(item: MainEntity)
    suspend fun upsertItem(item: MainEntity)
    suspend fun getItems(): MutableList<MainEntity>
}
