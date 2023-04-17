package com.naze.objectoneshot_ver2.data.local.dao

import androidx.room.*
import com.naze.objectoneshot_ver2.data.local.model.KeyResultWithTasks
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.ObjectiveWithKeyResults

@Dao
interface ObjectiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objective: Objective): Long

    @Update
    suspend fun update(objective: Objective)

    @Delete
    suspend fun delete(objective: Objective)

    @Transaction
    @Query("SELECT * FROM objectives WHERE progress >= 100 OR endDate < :currentTime")
    suspend fun getAchieveObjectives(currentTime: Long): List<ObjectiveWithKeyResults>

    @Transaction
    @Query("SELECT * FROM objectives WHERE progress < 100 AND endDate >= :currentTime")
    suspend fun getObjectives(currentTime: Long): List<ObjectiveWithKeyResults>

    @Query("SELECT * FROM objectives WHERE id = :id")
    suspend fun getObjectiveById(id: String) : Objective

    @Transaction
    @Query("SELECT * FROM objectives")
    suspend fun getObjectiveWithKeyResult() : List<ObjectiveWithKeyResults>
}