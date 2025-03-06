package com.priyank.squaresgithubrepoassessmentcoding.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.priyank.squaresgithubrepoassessmentcoding.repository.RepoRepository

class RepoViewModelFactory(val app : Application, private val repoRepository: RepoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RepoViewModel(app, repoRepository) as T
    }

}