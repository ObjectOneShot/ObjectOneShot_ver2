package com.naze.objectoneshot_ver2.data.local.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "objectives")
data class Objective(
    var title: String = "",
    var startDate: Long,
    var endDate: Long,
    var progress: Double,
    var complete: Boolean,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
) {
}
