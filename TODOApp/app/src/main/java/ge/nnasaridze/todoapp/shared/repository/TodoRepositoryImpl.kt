package ge.nnasaridze.todoapp.shared.repository

import android.app.Activity
import androidx.annotation.WorkerThread
import androidx.room.Room
import ge.nnasaridze.todoapp.shared.Observer
import ge.nnasaridze.todoapp.shared.repository.database.TodoDatabase

class TodoRepositoryImpl(private val activity: Activity) : TodoRepository {
    private val todoDAO = Room.databaseBuilder(
        activity.applicationContext,
        TodoDatabase::class.java,
        "todo_database"
    ).build().todoDao()

    private val observers = mutableListOf<Observer>()
    private var data = listOf<TodoEntity>()

    init {
        Thread { updateData() }.start()
    }

    override fun getItemById(id: Int): TodoEntity {
        for (item in data)
            if (item.id == id)
                return item
        return TodoEntity()
    }

    override fun getAllItems(): List<TodoEntity> {
        return data
    }

    override fun upsertItem(item: TodoEntity) {
        Thread {
            remove(item)
            insert(item)
            updateData()
        }.start()
    }

    override fun deleteItem(item: TodoEntity) {
        Thread {
            remove(item)
            updateData()
        }.start()
    }

    override fun register(observer: Observer) {
        observers.add(observer)
        observer.dataChanged()
    }

    override fun unregister(observer: Observer) {
        for (o in observers)
            if (o == observer)
                observers.remove(o)
    }

    private fun notifyObservers() {
        activity.runOnUiThread {
            for (observer in observers)
                observer.dataChanged()
        }
    }

    private fun updateData() {
        data = todoDAO.getItems()
        notifyObservers()
    }

    @WorkerThread
    private fun insert(item: TodoEntity) {
        todoDAO.insert(item)
        updateData()
    }

    @WorkerThread
    private fun remove(item: TodoEntity) {
        todoDAO.remove(item.id)
        updateData()
    }

    companion object {
        private lateinit var INSTANCE: TodoRepository

        fun getInstance(activity: Activity): TodoRepository {
            if (!::INSTANCE.isInitialized)
                INSTANCE = TodoRepositoryImpl(activity)
            return INSTANCE
        }
    }
}