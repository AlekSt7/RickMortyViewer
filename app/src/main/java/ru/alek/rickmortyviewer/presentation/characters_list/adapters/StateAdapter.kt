package ru.alek.rickmortyviewer.presentation.characters_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.alek.rickmortyviewer.databinding.LoadingStateRowBinding

class StateAdapter(private val retry: (() -> Unit)? = null) : LoadStateAdapter<StateAdapter.ItemHolder>() {

    override fun onBindViewHolder(holder: ItemHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ItemHolder(
        LoadingStateRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ItemHolder(private val binding: LoadingStateRowBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.buttonRetry.setOnClickListener {
                retry?.invoke()
            }
        }

        fun bind(loadState: LoadState){

            binding.apply {
                mainProgress.isVisible = loadState is LoadState.Loading
                errorWrapper.isVisible = loadState !is LoadState.Loading
            }
        }

    }

}