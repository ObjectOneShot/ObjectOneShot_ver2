package com.naze.objectoneshot_ver2.domain.repository

import androidx.lifecycle.LiveData
import com.naze.objectoneshot_ver2.data.local.model.ObjectiveWithKeyResultWithTask

interface ObjectiveRepository {
    suspend fun getObjectiveWithKeyResultWithTask(): List<ObjectiveWithKeyResultWithTask>
}