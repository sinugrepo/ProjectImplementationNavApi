package com.sinug.projectimplementationnavapi.data.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

//	@field:SerializedName("gists_url")
//	val gistsUrl: String? = null,
//
//	@field:SerializedName("repos_url")
//	val reposUrl: String? = null,
//
//	@field:SerializedName("following_url")
//	val followingUrl: String? = null,
//
//	@field:SerializedName("starred_url")
//	val starredUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null
)
