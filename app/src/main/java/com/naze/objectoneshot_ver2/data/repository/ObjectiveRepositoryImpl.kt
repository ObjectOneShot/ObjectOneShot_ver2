package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import javax.inject.Inject

class ObjectiveRepositoryImpl @Inject constructor(
    private val objectiveDao: ObjectiveDao
): ObjectiveRepository {
    override suspend fun insert(objective: Objective): Long {
        return objectiveDao.insert(objective)
    }

}