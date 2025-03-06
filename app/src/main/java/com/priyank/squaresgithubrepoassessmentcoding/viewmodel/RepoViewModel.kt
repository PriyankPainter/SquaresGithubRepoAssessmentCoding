package com.priyank.squaresgithubrepoassessmentcoding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.model.RepoModel
import com.priyank.squaresgithubrepoassessmentcoding.repository.RepoRepository
import kotlinx.coroutines.launch

class RepoViewModel(app : Application, private val repoRepository: RepoRepository) : AndroidViewModel(app) {

    var repoLiveData: MutableLiveData<ArrayList<RepoModel>>? = null

    fun getRepoList(): LiveData<ArrayList<RepoModel>> {
        repoLiveData = repoRepository.getRepoList()
        return repoLiveData as MutableLiveData<ArrayList<RepoModel>>
    }


    fun addRepo(repos : GithubRepos) =
        viewModelScope.launch {
            repoRepository.insertRepo(repos)
        }

    fun addMultipleRepo(repoList : ArrayList<GithubRepos>) =
        viewModelScope.launch {
            repoRepository.insertMultipleRepo(repoList)
        }

    fun addBookmarkRepo(repos : GithubRepos) =
        viewModelScope.launch {
            repoRepository.addBookmarkRepo(repos)
        }

    fun getAllRepo() = repoRepository.getAllRepo()
}