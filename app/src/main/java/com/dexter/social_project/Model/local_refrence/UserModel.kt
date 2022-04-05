package com.dexter.social_project.Model.local_refrence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userAccount")
data class UserModel(
    @PrimaryKey()
    val user_number : String,
    val user_password : String,
    val user_type : String
) {
    constructor() : this("","","")

}