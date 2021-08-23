package ge.nnasaridze.alarmapp.scenes.main.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MainEntity(
    var hour: Int,
    val minute: Int,
    var isActive: Boolean = false
) {
    @PrimaryKey
    var id: Int = hour * 100 + minute
}