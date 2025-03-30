package com.example.conectaao.data.dao

import androidx.room.*
import com.example.conectaao.data.entity.UserProfile
import com.example.conectaao.data.relation.UserWithProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE userId = :userId")
    suspend fun getUserProfileById(userId: Long): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userProfile: UserProfile): Long

    @Update
    suspend fun update(userProfile: UserProfile)

    @Delete
    suspend fun delete(userProfile: UserProfile)
    
    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithProfile(userId: Long): Flow<UserWithProfile?>
} 