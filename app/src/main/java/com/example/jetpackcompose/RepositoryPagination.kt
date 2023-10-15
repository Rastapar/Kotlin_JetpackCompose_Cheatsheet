package com.example.jetpackcompose

import kotlinx.coroutines.delay

class RepositoryPagination {
    private val remoteDataSource = (1..100).map {
        ListItemPagination(
            title = "Item $it",
            description = "Description $it"
        )
    }

    suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItemPagination>> {
        delay(2000)
        val startingIndex = page * pageSize
        return if(startingIndex + pageSize <= remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        } else {
            Result.success(emptyList())
        }
    }
}