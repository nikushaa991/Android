package ge.nnasaridze.alarmapp.scenes.main

import ge.nnasaridze.alarmapp.scenes.main.repository.MainEntity

interface MainView {
    fun updateRecycler()
    fun setThemePreference(theme: Int)
    fun setAppTheme(theme: Int)
    fun setThemeButtonText(text: String)
    fun displayRemoveDialog(
        acceptHandler: () -> Unit,
        declineHandler: () -> Unit
    )

    fun displayTimepicker()
    fun stopAlarm(item: MainEntity)
    fun startAlarm(item: MainEntity)
}

interface MainPresenter {
    fun onSwitchThemePressed()
    fun onAddPressed()

    fun onSwitchPressed(position: Int)
    fun onAlarmLongPressed(position: Int)
    fun getItemAt(position: Int): MainEntity
    fun getItemCount(): Int
    fun onTimePicked(hour: Int, minute: Int)
}

interface MainRecyclerAdapter {
    fun update()
}

interface MainViewHolder {
    fun bind(data: MainEntity)
}