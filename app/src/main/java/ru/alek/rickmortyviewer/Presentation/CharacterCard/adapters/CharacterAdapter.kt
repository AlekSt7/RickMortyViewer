package ru.alek.rickmortyviewer.Presentation.CharacterCard.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.alek.rickmortyviewer.Domain.Entities.CharacterModel
import ru.alek.rickmortyviewer.Domain.Entities.EpisodeModel
import ru.alek.rickmortyviewer.Domain.Entities.SimpleFilter
import ru.alek.rickmortyviewer.R
import ru.alek.rickmortyviewer.databinding.CharacterCardBinding
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class CharacterAdapter() : PagingDataAdapter<CharacterModel, CharacterAdapter.ItemHolder>(
    DiffCallBAck()
) {

    private var itemBindCallBack: ((character: CharacterModel)-> Unit)? = null
    private var shareButtonCallback: ((characterId: Int) -> Unit)? = null
    private var episodeFlow: Flow<EpisodeModel?>? = null
    private var lifecycleOwner: LifecycleOwner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
            CharacterCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        episodeFlow,
        lifecycleOwner,
        itemBindCallBack,
        shareButtonCallback
        )

    var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class DiffCallBAck : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel) = oldItem == newItem
    }

    fun onItemBind(itemBindCallBack: ((character: CharacterModel) -> Unit)?){
        this.itemBindCallBack = itemBindCallBack
    }

    fun onShareButtonClick(shareButtonCallback: (characterId: Int)-> Unit){
        this.shareButtonCallback = shareButtonCallback
    }

    fun setFlow(episodeFlow: Flow<EpisodeModel?>, lifecycleOwner: LifecycleOwner?){
        this.lifecycleOwner = lifecycleOwner
        this.episodeFlow = episodeFlow
    }

    inner class ItemHolder(private val binding: CharacterCardBinding, private val episodeFlow: Flow<EpisodeModel?>?,
                           private val lifecycleOwner: LifecycleOwner?, private var itemBindCallBack: ((character: CharacterModel)-> Unit)?,
                           private val shareButtonCallBack: ((characterId: Int)->Unit)?): RecyclerView.ViewHolder(binding.root) {

        private val linearSnapHelper = LinearSnapHelper()

        fun bind(character: CharacterModel){

            character.episodesChild = EpisodeModel(id = character.id)
            itemBindCallBack?.invoke(character)

            lifecycleOwner?.lifecycleScope?.launch {
                episodeFlow?.filter { it?.id == character.id }?.collect {
                    character.bind(it?.episodes) }
            }

            binding.apply {

                shareButton.setOnClickListener {
                    shareButtonCallBack?.invoke(character.id)
                }

                characterName.text = character.name

                val s = character.status
                val g = character.gender
                val c = "$s - $g"
                status.text = c

                val bg = indicator.background.current as GradientDrawable

                val resources = root.context.resources

                when (s) {
                    SimpleFilter.Status.ALIVE.value -> bg.setColor(resources.getColor(R.color.green_light))
                    SimpleFilter.Status.DEAD.value -> bg.setColor(Color.RED)
                        SimpleFilter.Status.UNKNOWN.value -> bg.setColor(Color.GRAY)
                }

                linearSnapHelper.attachToRecyclerView(episodes)

                character.bindListener {
                    character.episodesChild?.episodes?.apply {
                        progressBar.visibility = View.GONE
                        episodes.adapter = EpisodeAdapter(this)
                        episodes.visibility = View.VISIBLE
                    }

                }

                Glide.with(itemView.context)
                    .load(character.image)
                    .centerCrop()
                    .placeholder(R.drawable.gradient_1)
                        //.error(R.drawable.error_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mainImage)

            }
        }

    }

}