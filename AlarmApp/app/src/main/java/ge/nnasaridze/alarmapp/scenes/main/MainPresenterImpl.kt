package ge.nnasaridze.alarmapp.scenes.main

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import ge.nnasaridze.alarmapp.scenes.main.repository.MainEntity
import ge.nnasaridze.alarmapp.scenes.main.repository.MainRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainPresenterImpl(
    private val view: MainView,
    private val repository: MainRepository,
    private var theme: Int
) :
    MainPresenter {
    private val cal = Calendar.getInstance()
    private val items = mutableListOf<MainEntity>()


    init {
        configureTheme()
        GlobalScope.launch {
            val unfilteredItems = filterOldAlarms(repository.getItems())
            for (item in unfilteredItems)
                addItemToList(item)
        }
    }

    override fun onSwitchThemePressed() {
        theme = if (theme == MODE_NIGHT_NO) MODE_NIGHT_YES else MODE_NIGHT_NO
        view.setThemePreference(theme)
        configureTheme()
    }

    override fun onAddPressed() {
        view.displayTimepicker()
    }

    override fun onSwitchPressed(position: Int) {
        val item = items[position]
        val nextStatus = !item.isActive
        items[position].isActive = nextStatus
        upsertItem(item)
        if (nextStatus)
            view.startAlarm(item)
        else view.stopAlarm(item)
    }

    override fun onAlarmLongPressed(position: Int) {
        view.displayRemoveDialog(
            { removeItemAt(position) },
            {}
        )
    }

    override fun getItemAt(position: Int): MainEntity {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onTimePicked(hour: Int, minute: Int) {
        val item = MainEntity(hour, minute, true)
        if (isDuplicate(item))
            return
        val currHour = cal.get(Calendar.HOUR_OF_DAY)
        val currMinute = cal.get(Calendar.MINUTE)
        if (!isMoreRecent(hour, minute, currHour, currMinute))
            return
        if (item.isActive)
            view.startAlarm(item)
        upsertItem(item)
        addItemToList(item)
    }

    private fun isDuplicate(currItem: MainEntity): Boolean {
        for (item in items)
            if (item.id == currItem.id)
                return true
        return false
    }


    private fun addItemToList(item: MainEntity) {
        val idx = getCorrectIndex(item)
        items.add(idx, item)
        view.updateRecycler()
    }

    private fun getCorrectIndex(newItem: MainEntity): Int {
        var idx = 0
        for (item in items)
            if (isMoreRecent(newItem.hour, newItem.minute, item.hour, item.minute))
                idx++
            else break
        return idx
    }

    private fun removeItemAt(position: Int) {
        removeItem(items[position])
        if (items[position].isActive)
            view.stopAlarm(items[position])
        items.removeAt(position)
        view.updateRecycler()
    }

    private fun configureTheme() {
        view.setAppTheme(theme)
        view.setThemeButtonText("Switch to ${if (theme == MODE_NIGHT_NO) "dark" else "light"}")
    }

    private fun filterOldAlarms(unfiltered: MutableList<MainEntity>): MutableList<MainEntity> {
        val currHour = cal.get(Calendar.HOUR_OF_DAY)
        val currMinute = cal.get(Calendar.MINUTE)
        for (item in unfiltered)
            if (!isMoreRecent(item.hour, item.minute, currHour, currMinute) && item.isActive)
                item.isActive = false
        return unfiltered
    }

    private fun isMoreRecent(hour1: Int, minute1: Int, hour2: Int, minute2: Int): Boolean {
        return (hour1 > hour2) || (hour1 == hour2 && minute1 > minute2)
    }

    private fun upsertItem(item: MainEntity) {
        GlobalScope.launch {
            repository.upsertItem(item)
        }
    }

    private fun removeItem(item: MainEntity) {
        GlobalScope.launch {
            repository.removeItem(item)
        }
    }
}
