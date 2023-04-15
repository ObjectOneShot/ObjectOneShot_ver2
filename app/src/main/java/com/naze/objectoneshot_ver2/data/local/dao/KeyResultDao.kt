package com.naze.objectoneshot_ver2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.naze.objectoneshot_ver2.data.local.model.KeyResult

@Dao
interface KeyResultDao {
    @Insert
    suspend fun insert(keyResult: KeyResult)

    @Update
    suspend fun update(keyResult: KeyResult)

    @Delete
    suspend fun delete(keyResult: KeyResult)

    @Query("SELECT * FROM key_results WHERE objective_id = :objectiveId")
    suspend fun getKeyResultsByObjective(objectiveId: Long): List<KeyResult>
}