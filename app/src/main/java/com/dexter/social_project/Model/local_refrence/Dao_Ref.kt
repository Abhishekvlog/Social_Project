package com.dexter.social_project.Model.local_refrence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao_Ref {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData_in_database(dataEntity: Data_Entity)

    @Query("select * from data_database")
    fun getData_from_database() : LiveData<List<Data_Entity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(userModel: UserModel)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun signInData(userModel1: UserModel1)

    @Query("select * from userAccount")
    fun getData() : List<UserModel>

    @Query("Delete from userAccount")
    fun deleteData()
}