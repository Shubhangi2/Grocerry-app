package com.example.grocerryapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GrocerryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: GrocerryItems)

    @Delete
    fun delete(item: GrocerryItems)

    @Query("SELECT * FROM grocerry_items")
    fun getAllItems(): LiveData<List<GrocerryItems>>
}