package com.example.androidacademyhw.details

import androidx.recyclerview.widget.DiffUtil
import com.example.androidacademyhw.data.Actor

class ActorDiffCallback : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
        oldItem == newItem
}