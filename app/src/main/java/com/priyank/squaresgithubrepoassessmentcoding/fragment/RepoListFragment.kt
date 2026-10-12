package com.priyank.squaresgithubrepoassessmentcoding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyank.squaresgithubrepoassessmentcoding.adapter.RepoAdapter
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.viewmodel.RepoViewModel
import com.priyank.squaresgithubrepoassessmentcoding.view.MainActivity
import com.priyank.squaresgithubrepoassessmentcoding.R
import com.priyank.squaresgithubrepoassessmentcoding.databinding.FragmentRepoListBinding

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private var repoListBinding: FragmentRepoListBinding? = null
    private val binding get() = repoListBinding!!

    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        repoListBinding = FragmentRepoListBinding.inflate(inflater, container, false)
        return repoListBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoViewModel = (activity as MainActivity).repoViewModel

        setupRepoRecyclerView()
        setupRepoFromAPIToDB()
    }

    private fun updateUI(repos: List<GithubRepos>) {
        if (repos.isNotEmpty()) {
            repoListBinding?.rvRepoList?.visibility = View.VISIBLE
            repoListBinding?.pb?.visibility = View.GONE

            /*for (data in repos) {
                Log.e(
                    "REPO",
                    "ID : ${data.repoId} \nTitle : ${data.repoName} \nDesc : ${data.repoDesc} "
                )
            }*/
        } else {
            // Optionally handle empty state
            // repoListBinding?.rvRepoList?.visibility = View.GONE
        }
    }

    /*
    * Get List from API and Add to Room DB
    * if Database has no records available
    * */
    private fun setupRepoFromAPIToDB() {
        repoViewModel.getRepoList().observe(viewLifecycleOwner, Observer { repoList ->
            repoList?.let { list ->
                val arr = ArrayList<GithubRepos>()
                for (data in list) {
                    val repoId = data.id
                    val name = data.name ?: ""
                    val description = data.description ?: ""
                    val githubRepo = GithubRepos(repoId, name, description, data.stargazersCount, data.isBookmark)
                    arr.add(githubRepo)
                    Log.e("REPO-->", "Id : $repoId\nTitle : $name\nDesc : $description\nStar : ${data.stargazersCount}\nBookmark : ${data.isBookmark}")
                    Log.e("REPO-->", "----------------------")
                }
                repoViewModel.addMultipleRepo(arr)

                getAllRepoData()
            }
        })
    }

    /*
    * Setup Recyclerview and Adapter by observing data from DB
    * */
    private fun setupRepoRecyclerView() {
        repoAdapter = RepoAdapter()
        repoListBinding?.rvRepoList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = repoAdapter
        }

        getAllRepoData()
    }

    private fun getAllRepoData(){
        // Pass all data from getAllRepo() into RepoAdapter()

        repoViewModel.getAllRepo().observe(viewLifecycleOwner) { repoList ->
            Log.e("REPO-->", "===========================================")
            if (repoList != null) {
                Log.e("REPO-->", repoList.toString())
                repoAdapter.submitList(ArrayList(repoList))
                updateUI(repoList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repoListBinding = null
    }

}