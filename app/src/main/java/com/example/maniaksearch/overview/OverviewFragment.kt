package com.example.maniaksearch.overview

import android.os.Bundle
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
        const val QUERY_PARAM = "query_param"

        fun newInstance(query : String, queryParam: HashMap<String, String>): OverviewFragment {
            val args = Bundle()
            args.putString(QUERY, query)
            args.putSerializable(QUERY_PARAM, queryParam)
            val fragment = OverviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: OverviewViewModel by viewModels()
    var  queryParam: HashMap<String, String> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.query.value = it.getString(QUERY).toString()
            queryParam = it.getSerializable(QUERY_PARAM) as HashMap<String, String>
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apiResRecyclerView.adapter = ApiLinearAdapter()
        viewModel.query.observe(viewLifecycleOwner, { newQuery ->
           viewModel.getApiResults(newQuery, queryParam)
        }
        )

        return binding.root
    }



}
