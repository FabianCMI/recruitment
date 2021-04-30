package com.example.maniaksearch.overview

import ItunesApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String> = _status

    /**
     * Call on init so we can display status immediately.
     */
    init {
        getApiResults()
    }

    /**
     * Gets the results from the user's query Itunes api call
     */
    private fun getApiResults() {
        // Launch the api call on a background thread
        viewModelScope.launch {

                var queryParam: HashMap<String, String> = HashMap()
                queryParam["media"] = "movie"
                val listResult = ItunesApi.retrofitService.getResFromApi("star wars", queryParam)
                _status.value = "Success: ${listResult.results[2].trackName}"


        }
    }
}
