package com.ing.helloing3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "telephone") val telephone: String,
    @ColumnInfo(name = "description") val description: String
) {
    constructor() : this(0, "", "", "", "", "")
}
