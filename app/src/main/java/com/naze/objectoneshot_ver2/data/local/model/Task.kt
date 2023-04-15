package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = KeyResult::class,
            parentColumns = ["id"],
            childColumns = ["key_result_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    var content: String,
    var check: Boolean = false,
    val key_result_id: Int,
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0
)
