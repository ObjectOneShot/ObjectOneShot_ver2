package com.objectiveoneshot.objectiveoneshot.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.objectiveoneshot.objectiveoneshot.data.local.dao.KeyResultDao
import com.objectiveoneshot.objectiveoneshot.data.local.dao.ObjectiveDao
import com.objectiveoneshot.objectiveoneshot.data.local.dao.TaskDao
import com.objectiveoneshot.objectiveoneshot.data.local.model.KeyResult
import com.objectiveoneshot.objectiveoneshot.data.local.model.Objective
import com.objectiveoneshot.objectiveoneshot.data.local.model.Task

@Database(
    entities = [Objective::class, KeyResult::class, Task::class],
    version = 2
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun objectiveDao(): ObjectiveDao
    abstract fun keyResultDao(): KeyResultDao
    abstract fun taskDao(): TaskDao
}