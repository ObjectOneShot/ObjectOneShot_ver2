package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.model.ObjectiveWithKeyResultWithTask
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import javax.inject.Inject

class ObjectiveRepositoryImpl @Inject constructor(
    private val objectiveDao: ObjectiveDao
): ObjectiveRepository {
    override suspend fun getObjectiveWithKeyResultWithTask(): List<ObjectiveWithKeyResultWithTask> {
        return objectiveDao.getObjectiveWithKeyResultsWithTask()
    }
}