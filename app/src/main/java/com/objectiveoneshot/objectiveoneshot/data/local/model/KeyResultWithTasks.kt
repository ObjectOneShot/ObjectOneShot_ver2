package com.objectiveoneshot.objectiveoneshot.data.local.model

import androidx.room.Embedded
import androidx.room.Relation


data class KeyResultWithTasks(
    @Embedded val keyResult: KeyResult,
    @Relation(
        parentColumn = "id",
        entityColumn = "key_result_id"
    )
    val tasks: List<Task>
)
