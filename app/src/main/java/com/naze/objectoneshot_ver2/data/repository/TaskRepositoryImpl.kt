package com.naze.objectoneshot_ver2.data.repository

import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.dao.TaskDao
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import com.naze.objectoneshot_ver2.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {
}