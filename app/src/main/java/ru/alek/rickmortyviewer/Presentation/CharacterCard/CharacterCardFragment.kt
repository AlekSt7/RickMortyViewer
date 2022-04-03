package ru.alek.rickmortyviewer.Presentation.CharacterCard

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.alek.rickmortyviewer.Presentation.CharacterCard.adapters.CharacterAdapter
import ru.alek.rickmortyviewer.Presentation.CharacterCard.adapters.CharacterStateAdapter
import ru.alek.rickmortyviewer.R
import ru.alek.rickmortyviewer.databinding.CharacterCardFragmentBinding
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class CharacterCardFragment : DialogFragment() {

    private lateinit var viewModel: CharacterCardFragmentViewModel
    private lateinit var binding: CharacterCardFragmentBinding

    private val adapter = CharacterAdapter()

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = CharacterCardFragmentBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = CharacterCardFragmentBinding.inflate(inflater)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window!!.setLayout(width, height)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL


        binding.apply {
            characterSlider.setHasFixedSize(true)
            LinearSnapHelper().attachToRecyclerView(characterSlider)
            characterSlider.layoutManager = linearLayoutManager

            //stateAdapter = StateAdapter { adapter.retry() }
            val s = ItemTouchHelper(ItemSwipeCallBack(dialog))
            s.attachToRecyclerView(characterSlider)
            characterSlider.adapter = adapter

            return root
        }

    }

    class ItemSwipeCallBack(private val dialog: Dialog?): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP or ItemTouchHelper.DOWN){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            dialog?.cancel()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharacterCardFragmentViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.fetchResults(
                requireArguments().getInt(
                    resources.getString(R.string.character_card_fragment_arg_1)
                )
            ).observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }

        adapter.setFlow(viewModel.episodesFlow, viewLifecycleOwner)

        adapter.onItemBind {
            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.fetchEpisodes(it)
            }
        }

        adapter.onShareButtonClick {
            val shareIntent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://rickmortyviewer.com/character/$it")
                type = "text/plain"
            }
            startActivity(shareIntent)
        }



    }

}