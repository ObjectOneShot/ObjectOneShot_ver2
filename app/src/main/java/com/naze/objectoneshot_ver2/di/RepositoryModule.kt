package com.naze.objectoneshot_ver2.di

import com.naze.objectoneshot_ver2.data.local.dao.KeyResultDao
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.dao.TaskDao
import com.naze.objectoneshot_ver2.data.repository.KeyResultRepositoryImpl
import com.naze.objectoneshot_ver2.data.repository.ObjectiveRepositoryImpl
import com.naze.objectoneshot_ver2.data.repository.TaskRepositoryImpl
import com.naze.objectoneshot_ver2.domain.repository.KeyResultRepository
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import com.naze.objectoneshot_ver2.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideObjectiveRepository(objectiveDao: ObjectiveDao): ObjectiveRepository = ObjectiveRepositoryImpl(objectiveDao)

    @Provides
    fun provideKeyResultRepository(keyResultDao: KeyResultDao): KeyResultRepository = KeyResultRepositoryImpl(keyResultDao)

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

}