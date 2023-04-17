package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class ObjectiveWithKeyResults(
    @Embedded val objective: Objective,
    @Relation(
        parentColumn = "id",
        entityColumn = "objective_id"
    )
    val keyResults: List<KeyResult>?
)
