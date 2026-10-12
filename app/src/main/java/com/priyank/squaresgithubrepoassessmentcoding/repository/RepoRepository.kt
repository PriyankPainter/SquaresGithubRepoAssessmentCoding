package com.priyank.squaresgithubrepoassessmentcoding.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.priyank.squaresgithubrepoassessmentcoding.database.RepoDatabase
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.model.RepoModel
import com.priyank.squaresgithubrepoassessmentcoding.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoRepository (private val db : RepoDatabase) {

    /*
    * fetch Response from API and Pass using LiveData
    * */
    private var repoList = MutableLiveData<List<RepoModel>>()

    fun getRepoList(): MutableLiveData<List<RepoModel>> {

        val call = RetrofitClient.getApiInterface().getRepositories()

        call.enqueue(object : Callback<List<RepoModel>> {
            override fun onResponse(call: Call<List<RepoModel>>, response: Response<List<RepoModel>>) {
                repoList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<RepoModel>>, t: Throwable) {
//                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                Log.e("REPO-->", t.message.toString())
            }
        })

        return repoList
    }



    /*
    * Perform operations on Database
    * */
    suspend fun insertRepo(repos : GithubRepos) = db.getRepoDao().insertRepo(repos)
    suspend fun insertMultipleRepo(repoList : List<GithubRepos>) = db.getRepoDao().insertMultipleRepo(repoList)

    suspend fun addBookmarkRepo(repos : GithubRepos) = db.getRepoDao().updateRepo(repos)

    fun getAllRepo() = db.getRepoDao().getAllRepos()

}