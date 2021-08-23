package ge.nnasaridze.alarmapp.scenes.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import ge.nnasaridze.alarmapp.R
import ge.nnasaridze.alarmapp.databinding.ActivityMainBinding
import ge.nnasaridze.alarmapp.scenes.main.recycler.MainRecyclerAdapterImpl
import ge.nnasaridze.alarmapp.scenes.main.repository.MainEntity
import ge.nnasaridze.alarmapp.scenes.main.repository.MainRepositoryImpl
import java.util.*

class MainActivity : MainView, AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private lateinit var preferences: SharedPreferences
    private lateinit var alarmManager: AlarmManager
    private var adapter: MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = this.getPreferences(Context.MODE_PRIVATE)
        val theme = preferences.getInt(
            getString(R.string.theme),
            getCurrentNightMode(resources.configuration.uiMode)
        )
        presenter = MainPresenterImpl(this, MainRepositoryImpl.getInstance(this), theme)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


        with(binding) {
            colorTextButton.setOnClickListener { presenter.onSwitchThemePressed() }
            addButton.setOnClickListener { presenter.onAddPressed() }

            recycler.adapter = MainRecyclerAdapterImpl(presenter)
            recycler.layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = recycler.adapter as MainRecyclerAdapter
        }
        supportActionBar?.hide()
        setContentView(binding.root)
    }

    override fun setAppTheme(theme: Int) {
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    override fun setThemeButtonText(text: String) {
        binding.colorTextButton.text = text
    }

    override fun updateRecycler() {
        adapter?.update()
    }

    override fun setThemePreference(theme: Int) {
        preferences
            .edit()
            .putInt(getString(R.string.theme), theme)
            .apply()
    }

    override fun displayRemoveDialog(acceptHandler: () -> Unit, declineHandler: () -> Unit) {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete item?")
            .setCancelable(false)
            .setPositiveButton("YES") { dialog, _ ->
                acceptHandler()
                dialog?.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                declineHandler()
                dialog?.dismiss()
            }
            .show()
    }

    override fun displayTimepicker() {
        val cal = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hour, minute ->
                presenter.onTimePicked(hour, minute)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun startAlarm(item: MainEntity) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, item.hour)
            set(Calendar.MINUTE, item.minute)
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, getPendingIntent(item))
    }

    override fun stopAlarm(item: MainEntity) {
        alarmManager.cancel(getPendingIntent(item))
    }

    private fun getPendingIntent(item: MainEntity): PendingIntent {
        return PendingIntent.getBroadcast(
            this,
            item.id,
            Intent(this, MainAlarmReceiver::class.java).apply {
                putExtra(ID_EXTRA_KEY, item.id)
            },
            FLAG_CANCEL_CURRENT
        )
    }
}