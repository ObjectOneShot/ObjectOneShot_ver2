package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "objectives")
data class Objective(
    val title: String,
    val startDate: Long,
    val endDate: Long,
    val progress: Double,
    val complete: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
}
