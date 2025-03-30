package com.example.conectaao.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.conectaao.data.entity.User
import com.example.conectaao.data.entity.UserProfile

data class UserWithProfile(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val profile: UserProfile?
) 