package com.priyank.squaresgithubrepoassessmentcoding.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.priyank.squaresgithubrepoassessmentcoding.database.RepoDatabase
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.model.RepoModel
import com.priyank.squaresgithubrepoassessmentcoding.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoRepository (private var context : Context, private val db : RepoDatabase) {

    /*
    * fetch Response from API and Pass using LiveData
    * */
    private var repoList = MutableLiveData<ArrayList<RepoModel>>()

    fun getRepoList(): MutableLiveData<ArrayList<RepoModel>> {

        val call = RetrofitClient.getApiInterface().getRepositories()

        call.enqueue(object : Callback<ArrayList<RepoModel>> {
            override fun onResponse(call: Call<ArrayList<RepoModel>>, response: Response<ArrayList<RepoModel>>) {
                repoList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<RepoModel>>, t: Throwable) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                Log.e("REPO", t.message.toString())
            }
        })

        return repoList
    }



    /*
    * Perform operations on Database
    * */
    suspend fun insertRepo(repos : GithubRepos) = db.getRepoDao().insertRepo(repos)
    suspend fun insertMultipleRepo(repoList : ArrayList<GithubRepos>) = db.getRepoDao().insertMultipleRepo(repoList)

    suspend fun addBookmarkRepo(repos : GithubRepos) = db.getRepoDao().updateRepo(repos)

    fun getAllRepo() = db.getRepoDao().getAllRepos()

}