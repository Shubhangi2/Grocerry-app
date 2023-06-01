package com.example.grocerryapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GrocerryItems::class], version = 1)
abstract class GrocerryDatabase: RoomDatabase() {
    abstract fun getGrocerryDao() : GrocerryDao

    companion object{
        @Volatile
        private var instance: GrocerryDatabase ?=  null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

    private fun createDatabase(context: Context)=
        Room.databaseBuilder(context.applicationContext, GrocerryDatabase::class.java, "Grocerry.db").build()
    }


}