package com.example.money_splitter.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.money_splitter.entity.Community
import kotlinx.coroutines.flow.Flow


@Dao
interface CommunityDAO {

    //create community
    @Insert
    suspend fun insertCommunity(community: Community)

    //read communities
    @Query("SELECT * FROM communities")
    fun getAllCommunities(): Flow<List<Community>>

    //delete user
    @Delete
    suspend fun deleteCommunity(community: Community)

}