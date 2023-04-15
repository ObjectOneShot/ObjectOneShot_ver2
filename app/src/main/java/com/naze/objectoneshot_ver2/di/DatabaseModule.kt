package com.naze.objectoneshot_ver2.di

import android.content.Context
import androidx.room.Room
import com.naze.objectoneshot_ver2.data.local.dao.KeyResultDao
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.dao.TaskDao
import com.naze.objectoneshot_ver2.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    //TODO(fallbackToDestructiveMigrationFrom() 상황에 따라 변경 필요 개발 단계에서는 데이터 베이스 충돌 시 이전 데이터베이스 삭제가 맞다고 판단.)

    @Provides
    fun provideObjectiveDao(database: AppDatabase): ObjectiveDao = database.objectiveDao()

    @Provides
    fun provideKeyResultDao(database: AppDatabase): KeyResultDao = database.keyResultDao()

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao = database.taskDao()
}