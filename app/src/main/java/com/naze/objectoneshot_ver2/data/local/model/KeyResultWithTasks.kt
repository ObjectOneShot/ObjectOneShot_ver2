package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import org.jetbrains.annotations.NotNull


data class KeyResultWithTasks(
    @Embedded val keyResult: KeyResult,
    @Relation(
        parentColumn = "id",
        entityColumn = "key_result_id"
    )
    val tasks: List<Task>
)
