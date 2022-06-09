package ru.alek.rickmortyviewer.presentation.characters_list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.alek.rickmortyviewer.DiContainer
import ru.alek.rickmortyviewer.domain.entities.SimpleFilter
import ru.alek.rickmortyviewer.presentation.characters_list.adapters.ListAdapter
import ru.alek.rickmortyviewer.presentation.characters_list.adapters.StateAdapter
import ru.alek.rickmortyviewer.R
import ru.alek.rickmortyviewer.databinding.BottomSheetFilterLayoutBinding
import ru.alek.rickmortyviewer.databinding.CharactersListFragmentBinding


class CharactersListFragment : Fragment(R.layout.characters_list_fragment) {

    private lateinit var viewModel: CharactersListViewModel
    private lateinit var viewModelFactory: CharactersListViewModelFactory
    private lateinit var binding: CharactersListFragmentBinding
    private val adapter = ListAdapter()
    private lateinit var stateAdapter: StateAdapter
    private lateinit var simpleFilter: SimpleFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModelFactory = DiContainer.injectCharactersListViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CharactersListFragmentBinding.inflate(inflater)
        binding.apply {

            charactersList.setHasFixedSize(true)

            //navigation to characterCardFragment
            adapter.onItemClick {
                val bundle = Bundle()
                bundle.putInt(resources.getString(R.string.character_card_fragment_arg_1), it)
                findNavController().navigate(R.id.characterCardFragment, bundle)
            }

            retryButton.setOnClickListener { adapter.retry() }

            //retry action for paging state adapter
            stateAdapter = StateAdapter{ adapter.retry() }
            charactersList.adapter = adapter.withLoadStateFooter(stateAdapter)

            setToolbar(binding, inflater)
            setUIState(adapter) //Set data from server or error while data loading
            handleScrollingToTopWhenSearching(adapter) //Scroll list up while searching

            return root
        }
    }

    private fun setUIState(adapter: ListAdapter){
        binding.apply {
            adapter.addLoadStateListener {

                // show a retry button outside the list when refresh hits an error
                errorText.isVisible = it.refresh is LoadState.Error

                // progressbar displays whether refresh is occurring
                progress.isVisible = it.refresh is LoadState.Loading

                if(it.refresh is LoadState.Error){
                    if((it.refresh as LoadState.Error).error.message == "404"){
                        errorWrapper.visibility = View.VISIBLE
                        retryButton.visibility = View.GONE
                        errorText.text = resources.getString(R.string.not_found)
                    }else{
                        errorText.text = resources.getString(R.string.error)
                        errorWrapper.visibility = View.VISIBLE
                        retryButton.visibility = View.VISIBLE
                    }
                }else{
                    errorWrapper.visibility = View.GONE
                    retryButton.visibility = View.GONE
                }

            }
        }
    }

    private fun setToolbar(binding: CharactersListFragmentBinding, inflater: LayoutInflater){
        setSearchView()
        binding.fiterButton.setOnClickListener {
            showFilterDialog(inflater)
        }
    }

    private fun showFilterDialog(inflater: LayoutInflater){
        val binding = BottomSheetFilterLayoutBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(binding.root)
        initSearchFIlter(binding)
        dialog.setOnCancelListener {
            simpleFilter.statusId = binding.dsPickerStatus.selectedIndex
            simpleFilter.genderId = binding.dsPickerGender.selectedIndex
        }
        dialog.show()
    }

    private fun initSearchFIlter(binding: BottomSheetFilterLayoutBinding){
        binding.apply {
            dsPickerStatus.selectedIndex = simpleFilter.statusId
            dsPickerGender.selectedIndex = simpleFilter.genderId

            startSearchButton.setOnClickListener {
                val status = dsPickerStatus.selectedItem.toString()
                val gender = dsPickerGender.selectedItem.toString()
                if(status == "no"){
                    simpleFilter.status = null
                }else{
                    simpleFilter.status = status
                }
                if(gender == "no"){
                    simpleFilter.gender = null
                }else{
                    simpleFilter.gender = gender
                }
                viewModel.fetchResultsByFilter(simpleFilter)
            }
        }
    }

    private fun setSearchView(){

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                debouncer(newText)
                return true
            }

        })
    }

    var handler: Handler = Handler(Looper.getMainLooper())
    private fun debouncer(text: String){
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(
            {
                simpleFilter.name = text
                viewModel.fetchResultsByFilter(simpleFilter)
            },
            650)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, DiContainer.injectCharactersListViewModel())[CharactersListViewModel::class.java]
        observeData()
    }

    private fun observeData(){
        viewModel.results.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.simpleFilter.observe(viewLifecycleOwner) {
            simpleFilter = it
        }
    }

    //from YouTube :D
    private fun handleScrollingToTopWhenSearching(adapter: ListAdapter) = lifecycleScope.launch {
        // list should be scrolled to the 1st item (index = 0) if data has been reloaded:
        // (prev state = Loading, current state = NotLoading)
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.charactersList.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadStateFlow(adapter: ListAdapter): Flow<LoadState> {
        return adapter.loadStateFlow.map { it.refresh }
    }

    private fun Flow<LoadState>.simpleScan(count: Int): Flow<List<LoadState?>> {
        val items = List<LoadState?>(count) { null }
        return scan(items) { previous, value -> previous.drop(1) + value }
    }

}