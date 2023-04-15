package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "objectives")
data class Objective(
    var title: String = "",
    var startDate: Long,
    var endDate: Long,
    var progress: Double,
    var complete: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) {
}
