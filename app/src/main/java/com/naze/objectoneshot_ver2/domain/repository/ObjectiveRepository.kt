package com.naze.objectoneshot_ver2.domain.repository

import com.naze.objectoneshot_ver2.data.local.model.Objective

interface ObjectiveRepository {
    suspend fun insert(objective: Objective): Long
}