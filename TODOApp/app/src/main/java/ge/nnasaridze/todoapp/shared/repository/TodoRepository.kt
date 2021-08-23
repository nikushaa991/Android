package ge.nnasaridze.todoapp.shared.repository

import ge.nnasaridze.todoapp.shared.Observable

interface TodoRepository : Observable {
    fun getItemById(id: Int): TodoEntity
    fun getAllItems(): List<TodoEntity>
    fun upsertItem(item: TodoEntity)
    fun deleteItem(item: TodoEntity)
}