package com.objectiveoneshot.objectiveoneshot.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.objectiveoneshot.objectiveoneshot.data.local.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE key_result_id = :keyResultId")
    fun getTasksByKeyResult(keyResultId: Long): LiveData<List<Task>>
}