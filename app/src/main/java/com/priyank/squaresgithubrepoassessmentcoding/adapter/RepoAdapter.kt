package com.priyank.squaresgithubrepoassessmentcoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.priyank.squaresgithubrepoassessmentcoding.databinding.RepoListItemBinding
import com.priyank.squaresgithubrepoassessmentcoding.fragment.RepoListFragmentDirections
import com.priyank.squaresgithubrepoassessmentcoding.database.GithubRepos

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    class RepoViewHolder(val itemBinding: RepoListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    }

    private val differCallback = object : DiffUtil.ItemCallback<GithubRepos>(){
        override fun areItemsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.repoName == newItem.repoName &&
                    oldItem.repoDesc == newItem.repoDesc &&
                    oldItem.repoStars == newItem.repoStars &&
                    oldItem.isBookmark == newItem.isBookmark
        }

        override fun areContentsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(RepoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val currentRepo = differ.currentList[position]

        holder.itemBinding.tvRepoName.text = currentRepo.repoName
        holder.itemBinding.tvRepoStars.text = "Stars : ${currentRepo.repoStars}"

        holder.itemView.setOnClickListener {
            val direction = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(currentRepo)
            it.findNavController().navigate(direction)
        }
    }
}