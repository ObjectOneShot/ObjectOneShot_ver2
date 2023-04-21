package com.objectiveoneshot.objectiveoneshot.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = KeyResult::class,
            parentColumns = ["id"],
            childColumns = ["key_result_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("key_result_id")]
)
data class Task(
    var content: String,
    var check: Boolean = false,
    val key_result_id: String,
    @PrimaryKey
    val id : String = UUID.randomUUID().toString()
)
