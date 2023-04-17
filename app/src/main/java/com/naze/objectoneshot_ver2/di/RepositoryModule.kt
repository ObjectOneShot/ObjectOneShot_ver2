package com.naze.objectoneshot_ver2.di

import com.naze.objectoneshot_ver2.data.local.dao.KeyResultDao
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.dao.TaskDao
import com.naze.objectoneshot_ver2.data.repository.ObjectiveRepositoryImpl
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideObjectiveRepository(
        objectiveDao: ObjectiveDao,
        keyResultDao: KeyResultDao,
        taskDao: TaskDao
    ): ObjectiveRepository = ObjectiveRepositoryImpl(objectiveDao, keyResultDao, taskDao)
}