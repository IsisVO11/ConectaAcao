package com.example.conectaao.data.repository

import android.content.Context
import com.example.conectaao.data.AppDatabase
import com.example.conectaao.data.entity.User
import com.example.conectaao.data.entity.UserProfile
import com.example.conectaao.data.relation.UserWithProfile
import kotlinx.coroutines.flow.Flow

class UserRepository(private val context: Context) {
    private val userDao = AppDatabase.getDatabase(context).userDao()
    private val userProfileDao = AppDatabase.getDatabase(context).userProfileDao()

    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun getUserWithProfile(userId: Long): Flow<UserWithProfile?> {
        return userProfileDao.getUserWithProfile(userId)
    }

    suspend fun createUser(user: User, profile: UserProfile?): Long {
        val userId = userDao.insert(user)
        
        if (profile != null) {
            userProfileDao.insert(profile.copy(userId = userId))
        }
        
        return userId
    }

    suspend fun updateUserWithProfile(user: User, profile: UserProfile?) {
        userDao.update(user)
        
        if (profile != null) {
            val existingProfile = userProfileDao.getUserProfileById(user.id)
            if (existingProfile == null) {
                userProfileDao.insert(profile.copy(userId = user.id))
            } else {
                userProfileDao.update(profile.copy(userId = user.id))
            }
        }
    }

    fun getVoluntarios(): Flow<List<User>> {
        return userDao.getUsersByType("VOLUNTARIO")
    }

    fun getPcDs(): Flow<List<User>> {
        return userDao.getUsersByType("PCD")
    }

    fun getIdosos(): Flow<List<User>> {
        return userDao.getUsersByType("IDOSO")
    }
} 