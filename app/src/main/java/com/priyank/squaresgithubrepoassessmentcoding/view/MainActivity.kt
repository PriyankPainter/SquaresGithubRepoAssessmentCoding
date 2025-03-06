package com.priyank.squaresgithubrepoassessmentcoding.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinapilistdemo.rxjava_sample.constants.AppConfig
import com.priyank.squaresgithubrepoassessmentcoding.R
import com.priyank.squaresgithubrepoassessmentcoding.database.RepoDatabase
import com.priyank.squaresgithubrepoassessmentcoding.repository.RepoRepository
import com.priyank.squaresgithubrepoassessmentcoding.viewmodel.RepoViewModel
import com.priyank.squaresgithubrepoassessmentcoding.viewmodel.RepoViewModelFactory
import io.reactivex.Observer

class MainActivity : AppCompatActivity() {

    lateinit var repoViewModel: RepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

    }

    private fun setupViewModel(){
        val repository = RepoRepository(AppConfig.mContext!!, RepoDatabase(this))
        val viewModelproviderFactory = RepoViewModelFactory(application, repository)
        repoViewModel = ViewModelProvider(this, viewModelproviderFactory)[RepoViewModel::class.java]
    }
}