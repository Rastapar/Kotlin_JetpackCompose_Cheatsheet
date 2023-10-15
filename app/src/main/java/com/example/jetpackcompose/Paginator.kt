package com.example.jetpackcompose

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    suspend fun reset()
}