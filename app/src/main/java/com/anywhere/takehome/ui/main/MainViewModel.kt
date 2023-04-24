package com.anywhere.takehome.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anywhere.takehome.BuildConfig
import com.anywhere.takehome.data.remote.Result
import com.anywhere.takehome.data.remote.models.RelatedTopic
import com.anywhere.takehome.domain.TopicsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TopicsRepositoryImpl
) : ViewModel() {

    //original received list of topics from the repository as source of truth
    private val relatedTopics: MutableLiveData<List<RelatedTopic>> = MutableLiveData()

    // _currentTopic is the selection of the detail view
    // it updates either on user selection or filtering the results
    private var _currentTopic: MutableLiveData<RelatedTopic> = MutableLiveData<RelatedTopic>()
    //using Livedata to make the currentTopic Immutable
    val currentTopic: LiveData<RelatedTopic>
        get() = _currentTopic

    // To keep track of user search and as source for the _mediatorLiveData
    private val searchQuery = MutableLiveData<String>()

    // Using mediator live data to expose topics both
    // the filtered and unfiltered based on
    // the query as source is either change in relatedTopics or query
    private val _mediatorLiveData: MediatorLiveData<List<RelatedTopic>> = MediatorLiveData()
    //using Livedata to make the mediatorLiveData Immutable
    val mediatorLiveData: LiveData<List<RelatedTopic>>
        get() = _mediatorLiveData

    init {

        // Source #1 as the relatedTopics are received from the repository
        _mediatorLiveData.addSource(relatedTopics){
            _mediatorLiveData.postValue(relatedTopics.value)
        }

        // Source #2 as the search query changes and needs to present filtered data
        _mediatorLiveData.addSource(searchQuery) { query ->
            if (query.isNullOrBlank()){
                _mediatorLiveData.postValue(relatedTopics.value)
            } else {
                _mediatorLiveData.postValue(relatedTopics.value?.filter {
                    it.text.lowercase()
                        .contains(query.lowercase())
                })
            }
            // Selecting first item for detail view after filtering the results
            _currentTopic.postValue(_mediatorLiveData.value?.firstOrNull())
        }
    }

    /**
     * Search query is users filter query received from the Binding adapter as user types
     */
    fun onSearchQueryChanged(query: String) {
        searchQuery.postValue(query)
    }

    /**
     * On User selection to update the current Topic in Detail View
     */
    fun updateCurrentTopic(relatedTopic: RelatedTopic) {
        _currentTopic.postValue(relatedTopic)
    }

    /**
     * Loads data from the repository
     */
    fun load() {
        viewModelScope.launch {
            // BuildConfig.QUERY is defined in flavour to get Flavour specific data
            // BuildConfig.BASE_URL is also similarly defined in flavour
            when(val result = repository.getRelatedTopics(BuildConfig.QUERY)) {
                is Result.Success -> {
                    relatedTopics.postValue(result.data!!)
                    // Selecting first item for detail view after data is received
                    _currentTopic.postValue(result.data.firstOrNull())
                }
                else -> {
                    //TODO: handle the API Error and Exception
                }
            }
        }
    }
}