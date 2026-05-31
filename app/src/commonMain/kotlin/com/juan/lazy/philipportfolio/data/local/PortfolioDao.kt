package com.juan.lazy.philipportfolio.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio_table WHERE id = 0")
    suspend fun getPortfolio(): PortfolioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolio(portfolio: PortfolioEntity)
}
