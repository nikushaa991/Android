package ge.nnasaridze.todoapp.shared.repository

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity
class TodoEntity(
    var name: String,
    val checked: MutableList<String>,
    val unchecked: MutableList<String>,
    var pinned: Boolean = false
) {
    var lastEdited = System.currentTimeMillis()

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore
    var changed = false

    constructor() : this("", mutableListOf<String>(), mutableListOf<String>(), false)

    fun refreshTimeStamp() {
        lastEdited = System.currentTimeMillis()
    }
}

class StringListConverter {
    @TypeConverter
    fun fromString(s: String): MutableList<String> {
        return if (s == "") mutableListOf() else (s.split("|||").map { it }).toMutableList()
    }

    @TypeConverter
    fun toString(s: MutableList<String>): String {
        return s.joinToString(separator = "|||")
    }
}