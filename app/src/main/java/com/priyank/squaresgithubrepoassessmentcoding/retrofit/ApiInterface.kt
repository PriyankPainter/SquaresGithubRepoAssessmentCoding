package com.priyank.squaresgithubrepoassessmentcoding.retrofit

import androidx.lifecycle.LiveData
import com.priyank.squaresgithubrepoassessmentcoding.model.RepoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("orgs/square/repos")
    fun getRepositories(): Call<ArrayList<RepoModel>>

}