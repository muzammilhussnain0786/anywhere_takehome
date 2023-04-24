package com.anywhere.takehome.ui.topics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anywhere.takehome.data.remote.models.RelatedTopic
import com.anywhere.takehome.databinding.TopicItemBinding

class TopicsAdapter(private var onItemClicked: (RelatedTopic) -> Unit) : RecyclerView.Adapter<TopicsAdapter.TopicViewHolder>(){

    private var topics: List<RelatedTopic> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(TopicItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = topics.count()

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(topics[position])
        }
        holder.bind(topics[position])
    }

    fun submitTopics(newTopics: List<RelatedTopic>) {
        this.topics = newTopics
        notifyDataSetChanged()
    }

    class TopicViewHolder(private val binding: TopicItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(relatedTopic: RelatedTopic) {
            binding.characterName = relatedTopic.getCharacterName()
            binding.executePendingBindings()
        }

    }
}