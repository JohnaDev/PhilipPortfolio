package com.juan.lazy.philipportfolio.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [PortfolioEntity::class], version = 5, exportSchema = false)
@ConstructedBy(PortfolioDatabaseConstructor::class)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
}

// The replacement for the companion object's getDatabase
expect object PortfolioDatabaseConstructor : RoomDatabaseConstructor<PortfolioDatabase>

fun getRoomDatabase(
    builder: RoomDatabase.Builder<PortfolioDatabase>
): PortfolioDatabase {
    return builder
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}
