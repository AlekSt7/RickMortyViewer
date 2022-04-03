package ru.alek.rickmortyviewer.Presentation.CharacterCard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alek.rickmortyviewer.Domain.Entities.EpisodeModel
import ru.alek.rickmortyviewer.databinding.EpisodeRowBinding

class EpisodeAdapter(private var episodes: List<EpisodeModel.Episode>): RecyclerView.Adapter<EpisodeAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        EpisodeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    class ItemHolder(val binding: EpisodeRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(episode: EpisodeModel.Episode){
            binding.apply {
                episodeId.text = episode.episode
                name.text = episode.name
                airDate.text = episode.air_date
            }
        }

    }

}