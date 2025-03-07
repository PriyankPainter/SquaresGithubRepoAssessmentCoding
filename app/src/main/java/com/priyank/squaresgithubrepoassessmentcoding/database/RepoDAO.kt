package com.priyank.squaresgithubrepoassessmentcoding.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RepoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo : GithubRepos)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleRepo(repoList : List<GithubRepos>)

    @Update
    suspend fun updateRepo(repo : GithubRepos)

    @Query("SELECT * FROM github_repos order by id desc")
    fun getAllRepos() : MutableLiveData<List<GithubRepos>>
}