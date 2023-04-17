package com.naze.objectoneshot_ver2.domain.repository

import com.naze.objectoneshot_ver2.data.local.model.*

interface ObjectiveRepository {
    suspend fun insertObjective(objective: Objective)
    suspend fun insertKeyResult(keyResult: List<KeyResult>)
    suspend fun insertTask(task: Task)

    suspend fun getObjective(): List<ObjectiveWithKeyResults>
    suspend fun getAchieveObjective(): List<ObjectiveWithKeyResults>

    suspend fun getObjectiveById(id: String): Objective
    suspend fun getKeyResultWithTasksById(id: String): List<KeyResultWithTasks>

    suspend fun updateObjective(objective: Objective)
    suspend fun updateKeyResultWithTask(keyResult: List<KeyResult>, task: List<Task>, objectiveId: String)
}