package com.priyank.squaresgithubrepoassessmentcoding.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos
import com.priyank.squaresgithubrepoassessmentcoding.databinding.RepoListItemBinding
import com.priyank.squaresgithubrepoassessmentcoding.fragment.RepoListFragmentDirections

class RepoAdapter : ListAdapter<GithubRepos, RepoAdapter.RepoViewHolder>(RepoDiffCallback()) {//RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

class RepoViewHolder(val itemBinding: RepoListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root)

private class RepoDiffCallback : DiffUtil.ItemCallback<GithubRepos>() {
    override fun areItemsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean =
        oldItem.repoId == newItem.repoId

    override fun areContentsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean =
        oldItem == newItem
}

/*private val differCallback = object : DiffUtil.ItemCallback<GithubRepos>() {
    override fun areItemsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean {
        // areItemsTheSame should only compare the unique ID
        return oldItem.repoId == newItem.repoId
    }

    override fun areContentsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean {
        // areContentsTheSame compares the actual content
        return oldItem == newItem
    }
}*/

//val differ = AsyncListDiffer(this, differCallback)

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
    Log.d("RepoAdapter", "onCreateViewHolder: Inflating item layout")
    return RepoViewHolder(
        RepoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}

/*override fun getItemCount(): Int {
    val count = differ.currentList.size
    Log.d("RepoAdapter", "getItemCount: $count")
    return count
}*/

override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
    val currentRepo = getItem(position)//differ.currentList[position]
    Log.d(
        "RepoAdapter",
        "onBindViewHolder: Binding item at position $position, Name: ${currentRepo.repoName}"
    )
    Log.e(
        "REPO",
        "ID : ${currentRepo.repoId} \nTitle : ${currentRepo.repoName} \nDesc : ${currentRepo.repoDesc} "
    )

    holder.itemBinding.tvRepoName.text = currentRepo.repoName
    holder.itemBinding.tvRepoStars.text = "Stars : ${currentRepo.repoStars}"

    if (currentRepo.isBookmark) {
        holder.itemBinding.ivBookmarkFilled.visibility = View.VISIBLE
    } else {
        holder.itemBinding.ivBookmarkFilled.visibility = View.GONE
    }

    holder.itemView.setOnClickListener {
        val direction = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(currentRepo)
        it.findNavController().navigate(direction)
    }
}
}