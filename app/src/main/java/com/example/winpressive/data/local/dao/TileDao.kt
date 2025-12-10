package com.example.winpressive.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.winpressive.data.local.entity.TileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TileDao {
    @Query("SELECT * FROM tiles ORDER BY rowIndex ASC")
    fun getTiles(): Flow<List<TileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTile(tile: TileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTiles(tiles: List<TileEntity>)

    @Update
    suspend fun updateTile(tile: TileEntity)

    @Query("DELETE FROM tiles WHERE id = :tileId")
    suspend fun deleteTile(tileId: String)
    
    @Query("DELETE FROM tiles")
    suspend fun clearAll()
}
