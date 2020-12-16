package com.example.androidacademyhw.details

import androidx.recyclerview.widget.DiffUtil
import com.example.androidacademyhw.data.Actor

class ActorDiffCallback : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem == newItem
}