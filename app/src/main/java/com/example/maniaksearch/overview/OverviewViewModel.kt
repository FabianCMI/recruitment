package com.example.maniaksearch.overview

import ItunesApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maniaksearch.network.ApiListResults
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // Status of the api call
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    // Results of the call
    private val _res = MutableLiveData<ApiListResults>()
    val res: LiveData<ApiListResults> = _res

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
                try {
                    _res.value = ItunesApi.retrofitService.getResFromApi("star wars", queryParam)
                    _status.value = "${_res.value!!.resultCount} résultat(s) trouvé(s)"
                } catch (e: Exception) {
                    _status.value = "Impossible de charger les données :  ${e.message}"
                }


        }
    }
}
