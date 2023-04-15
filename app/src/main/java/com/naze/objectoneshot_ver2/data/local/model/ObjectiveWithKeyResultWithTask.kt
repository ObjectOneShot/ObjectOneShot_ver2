package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class ObjectiveWithKeyResultWithTask(
    @Embedded val objective: Objective,
    @Relation(
        parentColumn = "id",
        entityColumn = "objective_id"
    )
    val keyResult: List<KeyResultWithTask>
) {
}