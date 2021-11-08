package com.example.project3dicoding.userInterface.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3dicoding.databinding.ItemRowUserBinding

class GithubUserAdapter(private val listGithub: List<ListGithubMain>) : RecyclerView.Adapter<GithubUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, username, url, photo) = listGithub[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .circleCrop()
            .into(holder.binding.tvAvatarDetail)
        holder.binding.tvUsername.text = username
        holder.binding.tvUrl.text = url
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(username)
        }

    }

    override fun getItemCount(): Int = listGithub.size

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }
}