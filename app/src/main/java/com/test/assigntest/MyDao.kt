package com.test.assigntest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyDao {
    @Insert
    fun insert(myEntity: MyEntity)

    @Query("SELECT * FROM data_table")
    fun getAllEntities(): List<MyEntity>
}
