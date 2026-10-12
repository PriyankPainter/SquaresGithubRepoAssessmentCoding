package com.priyank.squaresgithubrepoassessmentcoding.repository

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priyank.squaresgithubrepoassessmentcoding.database.RepoDatabase
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.model.RepoModel
import com.priyank.squaresgithubrepoassessmentcoding.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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

    /**
     * Fetch data from local JSON file in assets folder
     */
    fun getRepoListFromAssets(assetManager: AssetManager): MutableLiveData<List<RepoModel>> {
        val jsonString: String
        try {
            jsonString = assetManager.open("API_DATA.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<RepoModel>>() {}.type
            val repos: List<RepoModel> = Gson().fromJson(jsonString, listType)
            repoList.postValue(repos)
        } catch (ioException: IOException) {
            Log.e("REPO-->", ioException.message.toString())
        }
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