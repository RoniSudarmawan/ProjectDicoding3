package com.example.project3dicoding.userInterface.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3dicoding.database.Favorite
import com.example.project3dicoding.databinding.ItemRowFavoriteBinding

class FavoriteUserAdapter (private val listFavorite: List<Favorite>) : RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onButtonClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnButtonClickCallback(onButtonClickCallback : OnItemClickCallback) {
        this.onButtonClickCallback = onButtonClickCallback
    }

    class ListViewHolder(var binding: ItemRowFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, username, url, photo) = listFavorite[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .circleCrop()
            .into(holder.binding.tvAvatarDetail)
        holder.binding.tvUsername.text = username
        holder.binding.tvUrl.text = url
        holder.itemView.setOnClickListener {
            if (username != null) {
                onItemClickCallback.onItemClicked(listFavorite[position])
            }
        }
        holder.binding.fabFavorite.setOnClickListener{onButtonClickCallback.onItemClicked(listFavorite[position])}

    }

    override fun getItemCount(): Int = listFavorite.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }
}