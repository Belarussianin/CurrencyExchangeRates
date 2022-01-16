package com.example.currencyexchangerates.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Flow<List<CurrencyDbEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME_ID = :id")
    suspend fun getById(id: Int): CurrencyDbEntity?

    //@Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME_NAME = :name")
    //suspend fun getByName(name: String): CurrencyDbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIfNotInDb(currencyDbEntity: CurrencyDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyDbEntity: CurrencyDbEntity)

    //@Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_NAME_ID = :id")
    //suspend fun deleteById(id: Int)

    @Delete
    suspend fun delete(currencyDbEntity: CurrencyDbEntity)
}