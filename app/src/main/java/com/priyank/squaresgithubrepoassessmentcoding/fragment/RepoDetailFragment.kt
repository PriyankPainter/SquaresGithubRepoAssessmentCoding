package com.priyank.squaresgithubrepoassessmentcoding.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.priyank.squaresgithubrepoassessmentcoding.view.MainActivity
import com.priyank.squaresgithubrepoassessmentcoding.R
import com.priyank.squaresgithubrepoassessmentcoding.databinding.FragmentRepoDetailBinding
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.viewmodel.RepoViewModel

class RepoDetailFragment : Fragment(R.layout.fragment_repo_detail) {

    private var repoDetailBinding : FragmentRepoDetailBinding? = null
    private val binding get() = repoDetailBinding

    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoDetailView : View
    private lateinit var currentRepo: GithubRepos
    private var name:String? = null
    private var desc:String? = null
    private var stars:Int = 0
    private val args : RepoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        repoDetailBinding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        repoViewModel = (activity as MainActivity).repoViewModel
        repoDetailView = view

        currentRepo = args.githubRepos!!

        name = currentRepo.repoName
        desc = currentRepo.repoDesc
        stars = currentRepo.repoStars

        repoDetailBinding!!.tvRepoName.setText(name)
        repoDetailBinding!!.tvRepoStars.setText("Stars : $stars")
        repoDetailBinding!!.tvRepoDesc.setText(desc)

        if(currentRepo.isBookmark){
            repoDetailBinding!!.ivBookmarkEmpty.visibility = View.GONE
            repoDetailBinding!!.ivBookmarkFilled.visibility = View.VISIBLE
        } else {
            repoDetailBinding!!.ivBookmarkFilled.visibility = View.GONE
            repoDetailBinding!!.ivBookmarkEmpty.visibility = View.VISIBLE
        }

        repoDetailBinding!!.ivBookmarkEmpty.setOnClickListener {
            try {
                addRemoveToBookmark(true)
            }catch (e: Exception){
                Log.e("REPO", "Issue adding to bookmark ---> " + e.message.toString())
            }
        }

        repoDetailBinding!!.ivBookmarkFilled.setOnClickListener {
            try {
                addRemoveToBookmark(false)
            }catch (e: Exception){
                Log.e("REPO", "Issue removing from bookmark ---> " + e.message.toString())
            }
        }
    }

    /*
    * Add and Remove item to bookmark
    * by setting TRUE or FALSE in Database
    * to Show BookMark ImageAccordingly
    * */
    fun addRemoveToBookmark(bookmarkValue : Boolean){
        val note = GithubRepos(name!!, desc!!, stars, bookmarkValue)
        repoViewModel.addBookmarkRepo(note)

        Toast.makeText(context, "Repository bookmarked successfully", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        repoDetailBinding = null
    }

}