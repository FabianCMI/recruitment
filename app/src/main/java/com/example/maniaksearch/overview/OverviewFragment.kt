package com.example.maniaksearch.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.maniaksearch.databinding.FragmentOverviewBinding

/**
 * Fragment showing the status of the Itunes api call to let the user know if it has failed.
 */
class OverviewFragment() : androidx.fragment.app.Fragment() {

    /**
     * Static factory creating the fragment to avoid having a constructor with arguments
     * (Recommended practice)
     */
    companion object {
        const val QUERY = "query"

        fun newInstance(query : String): OverviewFragment {
            val args = Bundle()
            args.putString(QUERY, query)
            val fragment = OverviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: OverviewViewModel by viewModels()
    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.query.value = it.getString(QUERY).toString()
        }
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apiResRecyclerView.adapter = ApiLinearAdapter()
        Log.d("OverviewViewModel","onCreateView ${viewModel.query.value}")
        viewModel.query.observe(viewLifecycleOwner, { newQuery ->
           viewModel.getApiResults(newQuery, HashMap<String, String>())
        }
        )

        return binding.root
    }



}
