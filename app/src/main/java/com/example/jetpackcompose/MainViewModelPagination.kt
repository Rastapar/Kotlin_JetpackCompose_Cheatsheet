package com.example.jetpackcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModelPagination: ViewModel() {
    var state by mutableStateOf(ScreenStatePagination())
    private val repository = RepositoryPagination() // This is made just for simplification of the example

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = {nextPage ->
            repository.getItems(nextPage, 20)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    // Launch a Coroutine loading for the items
    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}

data class ScreenStatePagination(
    val isLoading: Boolean = false,
    val items: List<ListItemPagination> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)