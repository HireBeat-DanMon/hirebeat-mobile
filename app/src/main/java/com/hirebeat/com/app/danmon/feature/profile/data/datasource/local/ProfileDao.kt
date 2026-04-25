package com.hirebeat.com.app.danmon.feature.profile.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hirebeat.com.app.danmon.feature.profile.data.datasource.local.entity.UserProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM my_profile WHERE id = 'ME' LIMIT 1")
    suspend fun getMyProfile(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyProfile(profile: UserProfileEntity)
}