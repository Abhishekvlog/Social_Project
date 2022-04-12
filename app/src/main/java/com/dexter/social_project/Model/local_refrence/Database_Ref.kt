package com.dexter.social_project.Model.local_refrence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserModel::class,UserModel1::class,Data_Entity::class], version = 2)
abstract class Database_Ref : RoomDatabase() {
    abstract fun getDao(): Dao_Ref

    companion object {
        private var instance: Database_Ref? = null
        fun get_Ref_Database(context: Context): Database_Ref {
            if (instance != null) {
                return instance!!
            } else {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    Database_Ref::class.java,
                    "Ref_db"
                )
                builder.fallbackToDestructiveMigration()
                instance = builder.build()
            }
            return instance!!
        }
    }
}