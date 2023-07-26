    package com.example.coinranking_baris.ui

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import android.widget.Spinner
    import androidx.appcompat.app.AppCompatDelegate
    import androidx.fragment.app.viewModels
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    import com.example.coinranking_baris.coins.CoinsAdapter
    import com.example.coinranking_baris.coins.CoinsViewModel
    import com.example.coinranking_baris.databinding.FragmentCoinsBinding
    import com.example.coinranking_baris.sharedprefs.SharedPrefs

    class CoinsFragment : Fragment() {
        private lateinit var binding: FragmentCoinsBinding
        private lateinit var adapter: CoinsAdapter
        private lateinit var recyclerView: RecyclerView
        private lateinit var coinsSpinner: Spinner
        private lateinit var swipeRefreshLayout: SwipeRefreshLayout
        private val viewModel: CoinsViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            binding = FragmentCoinsBinding.inflate(inflater)
            recyclerView = binding.recyclerView
            adapter = CoinsAdapter()
            recyclerView.adapter = adapter
            coinsSpinner = binding.coinsSpinner
            swipeRefreshLayout = binding.swipeRefreshLayout
            val isNightModeEnabled = SharedPrefs.getNightMode()

            binding.switch1.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    SharedPrefs.dayColorMode()
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    SharedPrefs.nightColorMode()
                }
            }

            binding.switch1.isChecked = isNightModeEnabled
            if (isNightModeEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager

            val sortOptionsAdapter = ArrayAdapter(
                coinsSpinner.context,
                android.R.layout.simple_spinner_item,
                CoinsViewModel.SORT_OPTIONS
            )
            sortOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            coinsSpinner.adapter = sortOptionsAdapter

            viewModel.coinList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            viewModel.uiState.observe(viewLifecycleOwner){
                swipeRefreshLayout.isRefreshing = it
            }

            swipeRefreshLayout.setOnRefreshListener {
                val selectedOptionId = CoinsViewModel.SORT_OPTIONS[coinsSpinner.selectedItemPosition]
                viewModel.refresh(selectedOptionId)
                swipeRefreshLayout.isRefreshing = false
            }

            coinsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedOption = CoinsViewModel.SORT_OPTIONS[position]
                    viewModel.applySortOption(selectedOption)
                    // get resources from R.array.sort_options and send the position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            return binding.root
        }
    }