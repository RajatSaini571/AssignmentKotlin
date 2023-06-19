package com.test.assigntest

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "data_table")
class MyEntity(var myList: ArrayList<DataModel>) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}