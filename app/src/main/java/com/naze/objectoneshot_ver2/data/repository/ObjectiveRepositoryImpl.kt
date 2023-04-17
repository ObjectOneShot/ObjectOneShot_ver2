package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.KeyResultDao
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.dao.TaskDao
import com.naze.objectoneshot_ver2.data.local.model.*
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import com.naze.objectoneshot_ver2.util.getCurrentDate
import javax.inject.Inject

class ObjectiveRepositoryImpl @Inject constructor(
    private val objectiveDao: ObjectiveDao,
    private val keyResultDao: KeyResultDao,
    private val taskDao: TaskDao
): ObjectiveRepository {
    override suspend fun insertObjective(objective: Objective) {
        objectiveDao.insert(objective)
    }

    override suspend fun insertKeyResult(keyResult: List<KeyResult>) {
        keyResultDao.insert(keyResult)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    override suspend fun getObjective(): List<ObjectiveWithKeyResults> {
        return objectiveDao.getObjectives(getCurrentDate())
    } //TODO (추후 변경 : KeyResult 데이터를 포함한 구조로 변경 필요)

override suspend fun getAchieveObjective(): List<ObjectiveWithKeyResults> {
        return objectiveDao.getAchieveObjectives(getCurrentDate())
    } //TODO (추후 변경 : KeyResult 데이터를 포함한 구조로 변경 필요)

    override suspend fun getObjectiveById(id: String): Objective {
        return objectiveDao.getObjectiveById(id)
    }

    override suspend fun getKeyResultWithTasksById(id: String): List<KeyResultWithTasks> {
        return keyResultDao.getKeyResultWithTasksById(id)
    }

}