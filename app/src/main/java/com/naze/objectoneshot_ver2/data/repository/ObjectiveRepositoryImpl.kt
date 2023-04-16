package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.KeyResultDao
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.dao.TaskDao
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.Task
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
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

}