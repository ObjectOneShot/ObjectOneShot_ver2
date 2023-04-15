package com.naze.objectoneshot_ver2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naze.objectoneshot_ver2.data.local.dao.ObjectiveDao
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.Task

@Database(
    entities = [Objective::class, KeyResult::class, Task::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun objectiveDao(): ObjectiveDao


}