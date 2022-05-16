package com.cs.foodapplandofcoding.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {

        if (attribute == null) {
            return ""
        } else {
            return attribute as String
        }
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
//                return attribute ?: ""
//        or
        if (attribute == null) {
            return ""
        } else {
            return attribute
        }
    }

}