package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.KeyResultDao
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.domain.repository.KeyResultRepository
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import javax.inject.Inject

class KeyResultRepositoryImpl @Inject constructor(
    private val keyResultDao: KeyResultDao
): KeyResultRepository {
}