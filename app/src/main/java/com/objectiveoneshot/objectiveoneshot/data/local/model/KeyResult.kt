package com.objectiveoneshot.objectiveoneshot.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "key_results",
    ignoredColumns = ["expand"],
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
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
): Expand() {

}

open class Expand {
    var expand: Boolean? = false
}
