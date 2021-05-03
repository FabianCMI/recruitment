package com.example.maniaksearch.overview

import ItunesApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maniaksearch.network.ApiListResults
import kotlinx.coroutines.launch

enum class ApiCallStatus { LOADING, ERROR, SUCCESS }

// Tag for logcat
const val TAG = "OverviewViewModel"

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // Status of the api call
    private val _status = MutableLiveData<ApiCallStatus>()
    val status: LiveData<ApiCallStatus> = _status
    // Results of the call
    private val _res = MutableLiveData<ApiListResults>()
    val res: LiveData<ApiListResults> = _res

    val query = MutableLiveData<String>()


    /**
     * Gets the results from the user's query Itunes api call
     */
    fun getApiResults(searchQuery: String, queryParam: HashMap<String, String>) {
        // Launch the api call on a background thread
        viewModelScope.launch {
                try {
                    _status.value = ApiCallStatus.LOADING
                    _res.value = query.value?.let { ItunesApi.retrofitService.getResFromApi(it, queryParam) }
                    _status.value = ApiCallStatus.SUCCESS
                } catch (e: Exception) {
                    _status.value = ApiCallStatus.ERROR
                }
        }
    }
}
