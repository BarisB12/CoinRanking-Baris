    package com.example.coinranking_baris.ui

    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import android.widget.ProgressBar
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
    import com.facebook.shimmer.ShimmerFrameLayout

    class CoinsFragment : Fragment() {
        private lateinit var binding: FragmentCoinsBinding
        private lateinit var adapter: CoinsAdapter
        private lateinit var recyclerView: RecyclerView
        private lateinit var coinsSpinner: Spinner
        private lateinit var swipeRefreshLayout: SwipeRefreshLayout
        private lateinit var shimmerView: ShimmerFrameLayout
        private lateinit var progressBar: ProgressBar
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
            progressBar = binding.progressBar

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

            viewModel.uiState.observe(viewLifecycleOwner) { isLoading ->
                swipeRefreshLayout.isRefreshing = isLoading

            }

            swipeRefreshLayout.setOnRefreshListener {
                val selectedOptions = CoinsViewModel.SORT_OPTS[coinsSpinner.selectedItemPosition]
                viewModel.refresh(selectedOptions)
            }

            viewModel.coinList.observe(viewLifecycleOwner) { coinList ->
                if (coinList.isNotEmpty()) {
                    shimmerView.stopShimmer()
                    shimmerView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                adapter.submitList(coinList)
            }

            var isLoadingSecondHalf = false
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val itemCount = layoutManager.itemCount

                    if (!isLoadingSecondHalf && lastVisibleItemPosition >= itemCount / 2) {
                        isLoadingSecondHalf = true
                        viewModel.loadNextPage()
                        progressBar.visibility = View.VISIBLE

                        Handler(Looper.getMainLooper()).postDelayed({
                            progressBar.visibility = View.GONE
                        }, 2000)
                    }

                    coinsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {

                            val selectedOption = CoinsViewModel.SORT_OPTS[position]
                            viewModel.refresh(selectedOption)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
                }
            })
            return binding.root
        }
    }