package com.priyank.squaresgithubrepoassessmentcoding.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "github_repos")
@Parcelize
data class GithubRepos(
    var repoName : String,
    val repoDesc : String,
    val repoStars : Int,
    val isBookmark : Boolean
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
        get() = field
        set(value) { field = value }
}
