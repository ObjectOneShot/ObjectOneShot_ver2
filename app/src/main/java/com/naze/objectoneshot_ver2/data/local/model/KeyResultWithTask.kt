package com.naze.objectoneshot_ver2.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class KeyResultWithTask(
    @Embedded val keyResult: KeyResult,
    @Relation(
        parentColumn = "id",
        entityColumn = "key_result_id"
    )
    val task: List<Task>
) {
}