package com.example.maniaksearch.callapi

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.maniaksearch.databinding.FragmentItunesResBinding

/**
 * Fragment showing the result of the Itunes api call
 */
class ItunesResFragment() : androidx.fragment.app.Fragment() {

    private var chosenCard: ISelectedCard? = null

    /**
     * Static factory creating the fragment to avoid having a constructor with arguments
     * (Recommended practice)
     */
    companion object {
        const val QUERY = "query"
        const val QUERY_PARAM = "query_param"

        fun newInstance(query: String, queryParam: HashMap<String, String>): ItunesResFragment {
            val args = Bundle()
            args.putString(QUERY, query)
            args.putSerializable(QUERY_PARAM, queryParam)
            val fragment = ItunesResFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: ItunesResViewModel by viewModels()
    var queryParam: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get query data from the main activity by the arguments attribute
        arguments?.let {
            viewModel.query.value = it.getString(QUERY).toString()
            queryParam = it.getSerializable(QUERY_PARAM) as HashMap<String, String>
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItunesResBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apiResRecyclerView.adapter = ApiLinearAdapter() {
            apiRes -> chosenCard?.onSelectedCard(
                (apiRes.collectionViewUrl ?: apiRes.trackViewUrl) ?: apiRes.previewUrl)
        }
        viewModel.query.observe(
                viewLifecycleOwner,
                { newQuery -> viewModel.getApiResults(newQuery, queryParam) }
        )

        return binding.root
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(requireActivity())
        try {
            chosenCard = activity as ISelectedCard?
        } catch (e: ClassCastException) {
            Log.e(com.example.maniaksearch.filters.TAG, "Activity doesn't implement the ISelectedCountry interface")
        }
    }

}


interface ISelectedCard {
    fun onSelectedCard(string: String?)
}