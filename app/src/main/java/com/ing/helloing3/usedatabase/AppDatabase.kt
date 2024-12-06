package com.ing.helloing3.usedatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ing.helloing3.daos.StudentDao
import com.ing.helloing3.daos.SubjectDao
import com.ing.helloing3.models.Student
import com.ing.helloing3.models.Subject

@Database(entities = [Student::class, Subject::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun subjectDao(): SubjectDao
}
