package com.dexter.social_project.Model.local_refrence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Data_database")
data class Data_Entity(
    @ColumnInfo(name = "Title") var Title : String,
    @ColumnInfo(name = "Desc") var Desc : String,
    @ColumnInfo(name = "number") var number : String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_data") var id_data = 0
}