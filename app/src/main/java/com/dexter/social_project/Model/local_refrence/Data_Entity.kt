package com.dexter.social_project.Model.local_refrence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Data_database")
data class Data_Entity(
    @ColumnInfo(name = "Title") var Title: String,
    @ColumnInfo(name = "Desc") var Desc: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "tag") var tag: String,
    @PrimaryKey
    @ColumnInfo(name = "id_data")
    var id_data : String
):Serializable {

    constructor() : this("","","","","")

}