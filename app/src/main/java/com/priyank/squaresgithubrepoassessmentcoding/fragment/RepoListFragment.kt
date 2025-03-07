package com.priyank.squaresgithubrepoassessmentcoding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyank.squaresgithubrepoassessmentcoding.adapter.RepoAdapter
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.viewmodel.RepoViewModel
import com.priyank.squaresgithubrepoassessmentcoding.view.MainActivity
import com.priyank.squaresgithubrepoassessmentcoding.R
import com.priyank.squaresgithubrepoassessmentcoding.databinding.FragmentRepoListBinding
import com.priyank.squaresgithubrepoassessmentcoding.model.RepoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private var repoListBinding: FragmentRepoListBinding? = null
    private val binding get() = repoListBinding!!

    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        repoListBinding = FragmentRepoListBinding.inflate(inflater, container, false)
        return repoListBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoViewModel = (activity as MainActivity).repoViewModel
        setupRepoFromAPIToDB()
        setupRepoRecyclerView()
    }

    private fun updateUI(repos: List<GithubRepos>) {
        if (repos.isNotEmpty()) {
            repoListBinding?.rvRepoList!!.visibility = View.VISIBLE

            for (data in repos) {
                Log.e(
                    "REPO",
                    "ID : ${data.id} \nTitle : ${data.repoName} \nDesc : ${data.repoDesc} "
                )
            }

        } else {
            repoListBinding?.rvRepoList!!.visibility = View.GONE
        }
    }

    /*
    * Get List from API and Add to Room DB
    * if Database has no records available
    * */
    private fun setupRepoFromAPIToDB() {
        if(repoViewModel.getAllRepo().value!!.isEmpty()) {
            repoViewModel.getRepoList().observe(viewLifecycleOwner, Observer { repoList ->
//            val arr = ArrayList<GithubRepos>()
                for (data in repoList) {
//                arr.add(GithubRepos(data.name!!, data.description!!, data.stargazersCount, data.isBookmark))
                    repoViewModel.addRepo(
                        GithubRepos(
                            data.name!!,
                            data.description!!,
                            data.stargazersCount,
                            data.isBookmark
                        )
                    )
                }
//            repoViewModel.addMultipleRepo(arr)
            })
        }
    }

    /*
    * Setup Recyclerview and Adapter by observing data from DB
    * */
    private fun setupRepoRecyclerView() {
        repoAdapter = RepoAdapter()
        binding.rvRepoList.apply {
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            adapter = repoAdapter
        }

        activity?.let {
            repoViewModel.getAllRepo().observe(viewLifecycleOwner) { repoList ->
                repoAdapter.differ.submitList(repoList)
                updateUI(repoList)
                repoListBinding?.pb!!.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repoListBinding = null
    }

}