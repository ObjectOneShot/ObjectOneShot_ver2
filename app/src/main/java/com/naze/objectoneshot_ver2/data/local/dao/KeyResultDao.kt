package com.naze.objectoneshot_ver2.data.local.dao

import androidx.room.*
import com.naze.objectoneshot_ver2.data.local.model.KeyResult

@Dao
interface KeyResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(keyResult:List<KeyResult>)

    @Update
    suspend fun update(keyResult: KeyResult)

    @Delete
    suspend fun delete(keyResult: KeyResult)

    @Query("SELECT * FROM key_results WHERE objective_id = :objectiveId")
    suspend fun getKeyResultsByObjective(objectiveId: Long): List<KeyResult>
}