package ge.nnasaridze.alarmapp.scenes.main

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

fun getCurrentNightMode(uiMode: Int): Int {
    return if (uiMode.and(UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_NO) MODE_NIGHT_NO else MODE_NIGHT_YES
}

fun getFormattedTime(hour: Int, minute: Int): String {
    return hour.toString().padStart(2, '0') + ":" + minute.toString().padStart(2, '0')
}

const val ID_EXTRA_KEY = "ID_EXTRA_KEY"