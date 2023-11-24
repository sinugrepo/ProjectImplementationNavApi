package com.sinug.projectimplementationnavapi.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sinug.projectimplementationnavapi.data.response.DetailUserResponse
import com.sinug.projectimplementationnavapi.databinding.ItemUsernameFollowBinding

class FollowerAdapter() : ListAdapter<DetailUserResponse, FollowerAdapter.MyViewHolder>(DIFF_CALLBACK){

    private val users = mutableListOf<DetailUserResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DetailUserResponse>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailUserResponse>() {
            override fun areItemsTheSame(oldItem: DetailUserResponse, newItem: DetailUserResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DetailUserResponse, newItem: DetailUserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding = ItemUsernameFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val username = users[position]
        holder.bind(username)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class MyViewHolder(private val binding: ItemUsernameFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: DetailUserResponse){
            val requestOptions = RequestOptions().transform(CircleCrop())

            binding.tvItem.text = "${follower.login}"
            Glide.with(binding.root.context)
                .load(follower.avatarUrl)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivProfile)
        }
    }


}