package ru.alek.rickmortyviewer.Presentation.CharactersList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.alek.rickmortyviewer.Domain.Entities.SimpleCharacterModel
import ru.alek.rickmortyviewer.R
import ru.alek.rickmortyviewer.databinding.CharacterListRowBinding

class ListAdapter : PagingDataAdapter<SimpleCharacterModel, ListAdapter.ItemHolder>(DiffCallBAck()) {

    private var onItemClick: ((characterId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
            CharacterListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class DiffCallBAck : DiffUtil.ItemCallback<SimpleCharacterModel>(){
        override fun areItemsTheSame(oldItem: SimpleCharacterModel, newItem: SimpleCharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SimpleCharacterModel, newItem: SimpleCharacterModel): Boolean {
            return oldItem == newItem
        }

    }

    class ItemHolder(private val binding: CharacterListRowBinding, private val onItemClick: ((characterId: Int) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: SimpleCharacterModel){
                binding.apply {
                    characterName.text = character.name
                    binding.root.context.also { context ->
                        ("${context.getString(R.string.species)} ${character.species}").also { species.text = it }
                        ("${context.getString(R.string.gender)} ${character.gender}").also { gender.text = it }
                    }

                    Glide.with(itemView.context)
                        .load(character.image)
                        .centerCrop()
                        .placeholder(R.drawable.gradient_1)
                        //.error(R.drawable.error_placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(preview)

                    mainWrapper.setOnClickListener {
                        onItemClick?.invoke(character.id)
                    }
                }
        }

    }

    fun onItemClick(onItemClick: (characterId: Int) -> Unit){
        this.onItemClick = onItemClick
    }

}