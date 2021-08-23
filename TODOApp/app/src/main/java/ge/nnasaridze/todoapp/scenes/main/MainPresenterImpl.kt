package ge.nnasaridze.todoapp.scenes.main

import android.app.Activity
import ge.nnasaridze.todoapp.shared.Constants.NEW
import ge.nnasaridze.todoapp.shared.Observer
import ge.nnasaridze.todoapp.shared.repository.TodoEntity
import ge.nnasaridze.todoapp.shared.repository.TodoRepository
import ge.nnasaridze.todoapp.shared.repository.TodoRepositoryImpl

class MainPresenterImpl(private val view: MainView, activity: Activity) : MainPresenter, Observer {
    private val repository: TodoRepository = TodoRepositoryImpl.getInstance(activity)

    private var pinnedDataUnfiltered = arrayListOf<TodoEntity>()
    private var otherDataUnfiltered = arrayListOf<TodoEntity>()
    private var pinnedData = ArrayList(pinnedDataUnfiltered)
    private var otherData = ArrayList(otherDataUnfiltered)

    init {
        repository.register(this)
    }

    override fun dataChanged() {
        val data = repository.getAllItems()
        val newPinned = arrayListOf<TodoEntity>()
        val newOther = arrayListOf<TodoEntity>()

        for (item in data)
            if (item.pinned)
                newPinned.add(item)
            else newOther.add(item)

        if (newPinned.size == 0 && pinnedDataUnfiltered.size != 0)
            view.hidePinned()
        else if (newPinned.size != 0 && pinnedDataUnfiltered.size == 0)
            view.showPinned()

        pinnedDataUnfiltered = newPinned
        otherDataUnfiltered = newOther

        view.resetFilter()
        view.refreshUI()
    }

    override fun onFilterChanged(text: CharSequence?) {
        val filter = text?.toString() ?: return

        val newPinned: ArrayList<TodoEntity> = ArrayList()
        for (item in pinnedDataUnfiltered)
            if (item.name.lowercase().contains(filter.lowercase()))
                newPinned.add(item)
        pinnedData = newPinned

        val newOther: ArrayList<TodoEntity> = ArrayList()
        for (item in otherDataUnfiltered)
            if (item.name.lowercase().contains(filter.lowercase()))
                newOther.add(item)

        otherData = newOther
        view.refreshUI()
    }

    override fun onFabPressed() {
        view.gotoEditActivity(NEW)
    }

    override fun getPinnedItemCount(): Int {
        return pinnedData.size
    }

    override fun getPinnedItemAtPosition(position: Int): MainTodoEntity {
        return MainTodoEntity(pinnedData[position])
    }

    override fun onPinnedItemPressed(position: Int) {
        view.gotoEditActivity(pinnedData[position].id)
    }

    override fun getOtherItemAtPosition(position: Int): MainTodoEntity {
        return MainTodoEntity(otherData[position])
    }

    override fun onOtherItemPressed(position: Int) {
        view.gotoEditActivity(otherData[position].id)
    }

    override fun getOtherItemCount(): Int {
        return otherData.size
    }
}