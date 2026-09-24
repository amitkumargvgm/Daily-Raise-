package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes ORDER BY id ASC")
    fun getAllQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE category = :category ORDER BY id ASC")
    fun getQuotesByCategory(category: String): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE id = :id LIMIT 1")
    fun getQuoteById(id: Int): Flow<Quote?>

    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuotesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<Quote>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: Quote): Long

    @Update
    suspend fun updateQuote(quote: Quote)

    @Query("UPDATE quotes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE quotes SET isUnlocked = 1 WHERE id = :id")
    suspend fun unlockQuote(id: Int)

    @Query("UPDATE quotes SET isUnlocked = 1 WHERE category = :category")
    suspend fun unlockCategory(category: String)

    @Query("UPDATE quotes SET isUnlocked = 1")
    suspend fun unlockAllQuotes()

    @Query("DELETE FROM quotes WHERE id = :id")
    suspend fun deleteQuote(id: Int)
}
