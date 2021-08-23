package ge.nnasaridze.alarmapp.scenes.main

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ge.nnasaridze.alarmapp.R
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE


class MainAlarmReceiver : BroadcastReceiver() {
    private val cal = Calendar.getInstance()
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra(ID_EXTRA_KEY, -1) ?: -1
        context?.let {
            val manager = NotificationManagerCompat.from(context)

            if (intent?.action == ACTION_SNOOZE) {
                manager.cancel(id)
                snooze(context, id)
                return
            }
            if (intent?.action == ACTION_DISMISS) {
                manager.cancel(id)
                return
            }

            val snoozeAction = generateActionButton(ACTION_SNOOZE, SNOOZE_BUTTON_NAME, id, context)
            val dismissAction =
                generateActionButton(ACTION_DISMISS, DISMISS_BUTTON_NAME, id, context)

            val pi = PendingIntent.getActivity(
                context, 0,
                Intent(context, MainActivity::class.java).apply {
                    putExtra(ID_EXTRA_KEY, id)
                },
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val timeText = getFormattedTime(cal.get(HOUR_OF_DAY), cal.get(MINUTE))
            val notification =
                getNotificationBuilder(context, manager)
                    .setSmallIcon(R.drawable.alarm_clock)
                    .setContentTitle(NOTIFICATION_NAME)
                    .setContentText(NOTIFICATION_TEXT + timeText)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .addAction(dismissAction)
                    .addAction(snoozeAction)
                    .build()
            manager.notify(id, notification)
        }
    }

    private fun getNotificationBuilder(
        context: Context,
        manager: NotificationManagerCompat
    ): NotificationCompat.Builder {
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    IMPORTANCE_HIGH
                )
            )
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setShowWhen(true)
        } else NotificationCompat.Builder(context)
    }

    private fun snooze(context: Context, id: Int) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            Intent(context, MainAlarmReceiver::class.java).apply {
                putExtra(ID_EXTRA_KEY, id)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis + 1000 * 60, snoozePendingIntent)
    }

    private fun generateActionButton(
        actionName: String,
        name: String,
        id: Int,
        context: Context
    ): NotificationCompat.Action {
        val pi = PendingIntent.getBroadcast(
            context,
            id,
            Intent(context, MainAlarmReceiver::class.java).apply {
                putExtra(ID_EXTRA_KEY, id)
                action = actionName
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action.Builder(
            R.drawable.alarm_clock,
            name,
            pi
        ).build()
    }

    companion object {
        private const val ACTION_SNOOZE = "ACTION_SNOOZE"
        private const val ACTION_DISMISS = "ACTION_DISMISS"

        private const val CHANNEL_ID = "ge.nnasaridze.alarmapp.ALARM_CHANNEL"
        private const val CHANNEL_NAME = "Alarm Channel"
        private const val NOTIFICATION_NAME = "Alarm Message!"

        private const val SNOOZE_BUTTON_NAME = "Snooze"
        private const val DISMISS_BUTTON_NAME = "Cancel"

        private const val NOTIFICATION_TEXT = "Alarm set on "
    }
}