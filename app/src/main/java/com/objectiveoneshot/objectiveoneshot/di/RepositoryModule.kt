package com.objectiveoneshot.objectiveoneshot.di

import com.objectiveoneshot.objectiveoneshot.data.local.dao.KeyResultDao
import com.objectiveoneshot.objectiveoneshot.data.local.dao.ObjectiveDao
import com.objectiveoneshot.objectiveoneshot.data.local.dao.TaskDao
import com.objectiveoneshot.objectiveoneshot.data.repository.ObjectiveRepositoryImpl
import com.objectiveoneshot.objectiveoneshot.domain.repository.ObjectiveRepository
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