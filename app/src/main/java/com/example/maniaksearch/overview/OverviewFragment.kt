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
class OverviewFragment : androidx.fragment.app.Fragment() {

    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apiResRecyclerView.adapter = ApiLinearAdapter()
        return binding.root
    }
}
