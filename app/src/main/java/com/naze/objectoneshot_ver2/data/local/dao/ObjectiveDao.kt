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

    @Update
    suspend fun updateCompleteObjective(objective: List<Objective>)

    @Delete
    suspend fun delete(objective: Objective)

    @Query("DELETE FROM objectives WHERE id = :id")
    suspend fun deleteObjective(id: String)

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

    @Query("SELECT * FROM objectives WHERE complete = 0 AND (progress >= 100)")
    suspend fun getObjectiveComplete() : List<Objective>

    @Query("SELECT * FROM objectives WHERE complete = 0 AND (endDate < :currentTime)")
    suspend fun getObjectiveUnComplete(currentTime: Long) : List<Objective>
}