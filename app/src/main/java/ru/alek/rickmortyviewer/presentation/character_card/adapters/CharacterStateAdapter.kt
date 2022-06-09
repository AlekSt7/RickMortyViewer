package ru.alek.rickmortyviewer.presentation.character_card.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alek.rickmortyviewer.databinding.LoadingCharacterCardBinding

class CharacterStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<CharacterStateAdapter.ItemHolder>() {

    override fun onBindViewHolder(holder: ItemHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ItemHolder(
        LoadingCharacterCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ItemHolder(private val binding: LoadingCharacterCardBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            //binding.buttonRetry.setOnClickListener {
              //  retry.invoke()
           // }
        }

        fun bind(loadState: LoadState){

            binding.apply {
                //mainProgress.isVisible = loadState is LoadState.Loading
               // errorWrapper.isVisible = loadState !is LoadState.Loading
            }
        }

    }

}