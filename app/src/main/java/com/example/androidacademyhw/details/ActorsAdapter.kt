package com.example.androidacademyhw.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademyhw.data.Actor
import com.example.androidacademyhw.databinding.ActorBinding

class ActorsAdapter : ListAdapter<Actor, ActorsAdapter.ViewHolder>(ActorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ActorBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Actor) {
            with(binding) {
                textName.text = item.name
                imageActor.setImageDrawable(ContextCompat.getDrawable(root.context, item.image))
            }
        }
    }
}