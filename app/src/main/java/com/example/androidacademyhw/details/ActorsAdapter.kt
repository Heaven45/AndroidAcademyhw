package com.example.androidacademyhw.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidacademyhw.R
import com.example.androidacademyhw.buildActorUrl
import com.example.androidacademyhw.data.models.Cast
import com.example.androidacademyhw.databinding.ActorBinding
//import com.example.androidacademyhw.databinding.ItemActorBinding

class ActorsAdapter : ListAdapter<Cast, ActorsAdapter.ViewHolder>(ActorDiffCallback()) {

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
        fun bind(item: Cast) {


            with(binding) {
                imageActor.load(item.profile_path?.let { buildActorUrl(it) }) {
                    placeholder(R.drawable.ic_launcher_foreground)
                    crossfade(500)
                    fallback(R.drawable.ic_launcher_foreground)
                }
                textName.text = item.name
            }
        }
    }

    class ActorDiffCallback : DiffUtil.ItemCallback<Cast>() {

        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
            oldItem == newItem
    }
}


//            with(binding) {
//                Glide.with(root.context)
//                    .load(item.pictureUrl)
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                    .into(imageActor)