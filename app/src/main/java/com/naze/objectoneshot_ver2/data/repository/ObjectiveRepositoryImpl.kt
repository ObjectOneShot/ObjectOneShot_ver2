package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import javax.inject.Inject

class ObjectiveRepositoryImpl @Inject constructor(
    private val objectiveDao: ObjectiveDao
): ObjectiveRepository {

}