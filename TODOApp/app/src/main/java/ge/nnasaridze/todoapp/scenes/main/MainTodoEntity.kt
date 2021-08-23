package ge.nnasaridze.todoapp.scenes.main

import android.view.View
import ge.nnasaridze.todoapp.shared.repository.TodoEntity

class MainTodoEntity(data: TodoEntity) {
    val name = data.name

    val text1 = data.unchecked.getOrElse(0) { "" }
    val visibility1 = if (text1 == "") View.GONE else View.VISIBLE

    val text2 = data.unchecked.getOrElse(1) { "" }
    val visibility2 = if (text2 == "") View.GONE else View.VISIBLE

    val text3 = data.unchecked.getOrElse(2) { "" }
    val visibility3 = if (text3 == "") View.GONE else View.VISIBLE

    val ellipsesVisibility = if (data.unchecked.size <= 3) View.GONE else View.VISIBLE

    val pinnedCount = data.checked.size
    val pinnedVisibility: Int = if (pinnedCount == 0) View.GONE else View.VISIBLE
}