package com.priyank.squaresgithubrepoassessmentcoding.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GithubRepos::class], version = 1)
abstract class RepoDatabase : RoomDatabase(){

    abstract fun getRepoDao() : RepoDAO

    companion object{
        @Volatile
        private var instance : RepoDatabase ? = null
        private val LOCK = Any()

        operator fun invoke(context:  Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, RepoDatabase::class.java, "github_repo_db").build()
    }
}