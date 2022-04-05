package com.dexter.social_project.Model.local_refrence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_sign_table")
data class UserModel1(
    @PrimaryKey()
    val user_sign_number: String,
    val user_sign_email: String,
    val user_sign_password: String,
    val user_sign_type: String
) {
    constructor() : this("", "", "","")

}