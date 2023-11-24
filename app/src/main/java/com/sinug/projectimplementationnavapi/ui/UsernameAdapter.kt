package com.sinug.projectimplementationnavapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sinug.projectimplementationnavapi.data.response.ItemsItem
import com.sinug.projectimplementationnavapi.databinding.ItemUsernameBinding

class UsernameAdapter(private var onItemClickCallback: OnItemClickCallback) : ListAdapter<ItemsItem, UsernameAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsernameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val username = getItem(position)
        holder.bind(username)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(username) }
    }

//    override fun getItemCount(): Int = ItemsItem.size

    class MyViewHolder(private val binding: ItemUsernameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(username: ItemsItem){
            val requestOptions = RequestOptions().transform(CircleCrop())

            binding.tvItem.text = "${username.login}"
            Glide.with(binding.root.context)
                .load(username.avatarUrl)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivProfile)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
