package com.example.currencyexchangerates.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyexchangerates.data.database.COLUMN_NAME_ABBREVIATION
import com.example.currencyexchangerates.data.database.COLUMN_NAME_ID
import com.example.currencyexchangerates.data.database.COLUMN_NAME_NAME
import com.example.currencyexchangerates.data.database.COLUMN_NAME_POSITION
import com.example.currencyexchangerates.data.database.COLUMN_NAME_RATE
import com.example.currencyexchangerates.data.database.COLUMN_NAME_SCALE
import com.example.currencyexchangerates.data.database.COLUMN_NAME_STATE
import com.example.currencyexchangerates.data.database.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CurrencyDbEntity(
    @ColumnInfo(name = COLUMN_NAME_ABBREVIATION) val abbreviation: String,
    @PrimaryKey @ColumnInfo(name = COLUMN_NAME_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME_NAME) val name: String,
    @ColumnInfo(name = COLUMN_NAME_RATE) val rate: Double,
    @ColumnInfo(name = COLUMN_NAME_SCALE) val scale: Int,
    @ColumnInfo(name = COLUMN_NAME_STATE) val isActive: Boolean = true,
    @ColumnInfo(name = COLUMN_NAME_POSITION) val position: Int? = null
)