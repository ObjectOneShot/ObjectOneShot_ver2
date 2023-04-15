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
    val content: String,
    val check: Boolean,
    val key_result_id: Int,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)
