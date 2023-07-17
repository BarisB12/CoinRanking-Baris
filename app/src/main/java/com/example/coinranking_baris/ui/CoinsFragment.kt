    package com.example.coinranking_baris.ui

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import android.widget.Spinner
    import androidx.fragment.app.viewModels
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.coinranking_baris.R
    import com.example.coinranking_baris.coins.CoinsAdapter
    import com.example.coinranking_baris.coins.CoinsViewModel
    import com.example.coinranking_baris.databinding.FragmentCoinsBinding

    class CoinsFragment : Fragment() {
        private lateinit var binding: FragmentCoinsBinding
        private lateinit var adapter: CoinsAdapter
        private lateinit var recyclerView: RecyclerView
        private lateinit var coinsSpinner: Spinner

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

            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager

            ArrayAdapter.createFromResource(
                coinsSpinner.context,
                R.array.sort_options,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                coinsSpinner.adapter = adapter
            }

            viewModel.coinList.observe(viewLifecycleOwner) {

                adapter.submitList(it)
            }
            coinsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedOption = parent?.getItemAtPosition(position) as String
                    viewModel.applySortOption(selectedOption)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            return binding.root
        }
    }