package com.objectiveoneshot.objectiveoneshot.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "key_results",
    foreignKeys = [
        ForeignKey(
            entity = Objective::class,
            parentColumns = ["id"],
            childColumns = ["objective_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("objective_id")]
)
data class KeyResult(
    var title: String,
    var progress: Double,
    val objective_id: String,
    var isExpand: Boolean = false,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
) {

}
