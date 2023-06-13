package com.example.framewiz.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.framewiz.data.FrameData
import com.example.framewiz.data.FrameResponse
import com.example.framewiz.databinding.FrameItemBinding

class MainAdapter(private var listFrame: List<FrameData>) : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = FrameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val frame = listFrame[position]

        Glide.with(holder.itemView.context).load(frame.imageUrl).into(holder.binding.frameIV)
        holder.binding.frameTV.text = frame.name
    }

    override fun getItemCount(): Int = listFrame.size

    class UserViewHolder(var binding: FrameItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val USERNAME_KEY = "username_key"
    }
}