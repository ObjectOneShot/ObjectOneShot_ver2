package com.naze.objectoneshot_ver2.data.local.dao

import androidx.room.*
import com.naze.objectoneshot_ver2.data.local.model.Objective

@Dao
interface ObjectiveDao {
    @Insert
    suspend fun insert(objective: Objective)

    @Update
    suspend fun update(objective: Objective)

    @Delete
    suspend fun delete(objective: Objective)

    @Query("SELECT * FROM objectives")
    suspend fun getObjectives(): List<Objective>

}