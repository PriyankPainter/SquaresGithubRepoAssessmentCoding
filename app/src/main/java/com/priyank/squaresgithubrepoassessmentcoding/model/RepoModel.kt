package com.priyank.squaresgithubrepoassessmentcoding.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RepoModel {

    @SerializedName("id")
    @Expose
    public var id: Int = 0
        get() = field
        set(value) { field = value }

    @SerializedName("name")
    @Expose
    public var name: String? = null
        get() = field
        set(value) { field = value }


    @SerializedName("description")
    @Expose
    public var description: String? = null
        get() = field
        set(value) { field = value }


    @SerializedName("stargazers_count")
    @Expose
    public var stargazersCount: Int = 0
        get() = field
        set(value) { field = value }


    public var isBookmark: Boolean = false
        get() = field
        set(value) { field = value }

}