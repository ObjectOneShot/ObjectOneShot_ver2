package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "key_results",
    foreignKeys = [
        ForeignKey(
            entity = Objective::class,
            parentColumns = ["id"],
            childColumns = ["objective_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class KeyResult(
    val title: String,
    val progress: Double,
    val objective_id: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int
) {

}
