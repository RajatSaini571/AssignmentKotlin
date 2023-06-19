package com.test.assigntest

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<DataModel> {
        val listType = object : TypeToken<ArrayList<DataModel?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: ArrayList<DataModel?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
