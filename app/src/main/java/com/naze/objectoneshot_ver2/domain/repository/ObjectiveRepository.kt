package com.naze.objectoneshot_ver2.domain.repository

import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.Task

interface ObjectiveRepository {
    suspend fun insertObjective(objective: Objective)
    suspend fun insertKeyResult(keyResult: List<KeyResult>)
    suspend fun insertTask(task: Task)

    suspend fun getObjective(): List<Objective>
    suspend fun getAchieveObjective(): List<Objective>

    suspend fun getObjectiveById(id: String): Objective

}