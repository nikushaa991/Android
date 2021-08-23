package ge.nnasaridze.todoapp.scenes.edit

import android.app.Activity
import ge.nnasaridze.todoapp.shared.Constants.NEW
import ge.nnasaridze.todoapp.shared.Observer
import ge.nnasaridze.todoapp.shared.repository.TodoEntity
import ge.nnasaridze.todoapp.shared.repository.TodoRepository
import ge.nnasaridze.todoapp.shared.repository.TodoRepositoryImpl

class EditPresenterImpl(private var id: Int, private val view: EditView, activity: Activity) :
    EditPresenter, Observer {

    private val repository: TodoRepository = TodoRepositoryImpl.getInstance(activity)
    private var data = TodoEntity()

    init {
        if (id != NEW)
            repository.register(this)
        else
            view.focusName()
    }

    override fun onRemovePressed(position: Int) {
        data.changed = true
        data.unchecked.removeAt(position)
        view.refreshUI()
    }

    override fun onBackPressed() {
        cleanEmptyStrings()

        if (data.checked.isEmpty() && data.unchecked.isEmpty())
            repository.deleteItem(data)
        else if (data.changed) {
            data.refreshTimeStamp()
            repository.upsertItem(data)
        }
        repository.unregister(this)
        view.gotoMainActivity()
    }

    override fun onPinPressed() {
        data.changed = true
        data.pinned = !data.pinned
        if (data.pinned)
            view.setPinnedOn()
        else view.setPinnedOff()
    }

    override fun onAddPressed(item: String) {
        if (data.unchecked.isNotEmpty() && data.unchecked.last() == "")
            return

        data.changed = true
        data.unchecked.add(item)
        view.refreshUI()
    }

    override fun onNameChanged(s: CharSequence?) {
        data.changed = true
        data.name = s.toString()
    }

    override fun onItemTextUpdated(position: Int, text: String) {
        data.changed = true
        data.unchecked[position] = text
    }

    override fun getUncheckedItemAtPosition(position: Int): EditTodoEntity {
        val deletable = position == data.unchecked.size - 1
        return EditTodoEntity(data.unchecked[position], deletable)
    }

    override fun onUncheckedItemPressed(position: Int) {
        if (data.unchecked[position] == "")
            return

        data.changed = true
        val item = data.unchecked.removeAt(position)
        data.checked.add(item)

        if (data.checked.size == 1)
            view.showChecked()

        view.refreshUI()
    }

    override fun getCheckedItemAtPosition(position: Int): EditTodoEntity {
        return EditTodoEntity(data.checked[position])
    }

    override fun onCheckedItemPressed(position: Int) {
        data.changed = true
        val item = data.checked.removeAt(position)

        if (data.unchecked.isNotEmpty() && data.unchecked.last() == "") {
            data.unchecked.removeLast()
            data.unchecked.add(item)
            data.unchecked.add("")
        } else data.unchecked.add(item)

        if (data.checked.isEmpty())
            view.hideChecked()
        view.refreshUI()
    }

    override fun getUncheckedItemCount(): Int {
        return data.unchecked.size
    }

    override fun getCheckedItemCount(): Int {
        return data.checked.size
    }

    override fun dataChanged() {
        data = repository.getItemById(id)

        view.setName(data.name)
        if (data.name.isBlank())
            view.focusName()

        if (data.pinned)
            view.setPinnedOn()
        else view.setPinnedOff()

        view.refreshUI()
    }

    private fun cleanEmptyStrings() {
        for (item in data.unchecked)
            if (item == "")
                data.unchecked.remove(item)
    }
}
