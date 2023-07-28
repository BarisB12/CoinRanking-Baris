    package com.example.coinranking_baris.ui

    import android.os.Bundle
    import android.os.Handler
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import android.widget.Spinner
    import androidx.appcompat.app.AppCompatDelegate
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.lifecycleScope
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    import com.example.coinranking_baris.coins.CoinsAdapter
    import com.example.coinranking_baris.coins.CoinsViewModel
    import com.example.coinranking_baris.databinding.FragmentCoinsBinding
    import com.example.coinranking_baris.sharedprefs.SharedPrefs
    import com.facebook.shimmer.ShimmerFrameLayout
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.launch

    class CoinsFragment : Fragment() {
        private lateinit var binding: FragmentCoinsBinding
        private lateinit var adapter: CoinsAdapter
        private lateinit var recyclerView: RecyclerView
        private lateinit var coinsSpinner: Spinner
        private lateinit var swipeRefreshLayout: SwipeRefreshLayout
        private lateinit var shimmerView: ShimmerFrameLayout
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
            shimmerView = binding.shimmerView

            val isNightModeEnabled = SharedPrefs.getNightMode()

            binding.switch1.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    SharedPrefs.dayColorMode()
                } else {
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

            val sortOptionIds = CoinsViewModel.SORT_OPTIONS
            val sortOptions = sortOptionIds.map { getString(it) }

            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            coinsSpinner.adapter = spinnerAdapter

            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager

            viewModel.coinList.observe(viewLifecycleOwner) { coinList ->
                adapter.submitList(coinList)
            }

            viewModel.uiState.observe(viewLifecycleOwner) { isLoading ->
                swipeRefreshLayout.isRefreshing = isLoading
                shimmerView.startShimmer()
                shimmerView.visibility = View.VISIBLE

                recyclerView.visibility = View.GONE

                if (!isLoading) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(2500)
                        shimmerView.stopShimmer()
                        shimmerView.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
            }

            swipeRefreshLayout.setOnRefreshListener {
                val selectedOptionId = CoinsViewModel.SORT_OPTIONS[coinsSpinner.selectedItemPosition]
                viewModel.refresh(selectedOptionId)
            }

            coinsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    val selectedOption = CoinsViewModel.SORT_OPTIONS[position]
                    viewModel.refresh(selectedOption)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            return binding.root
        }
    }