package com.juan.lazy.philipportfolio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio_table")
data class PortfolioEntity(
    @PrimaryKey val id: Int = 0,
    val jsonContent: String
)
