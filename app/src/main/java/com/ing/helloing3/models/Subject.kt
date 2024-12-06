package com.ing.helloing3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "enseignant") val enseignant: String,
    @ColumnInfo(name = "horaire") val horaire: Int,
    @ColumnInfo(name = "coef") val coef: Int
) {
    fun toPresentation(): String {
        return "Code : $code\nEnseignant : $enseignant\nHoraire : $horaire H\nCoef : $coef"
    }
}
