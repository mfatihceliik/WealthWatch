package com.example.wealthwatch.domain.repository.local.search_history

import com.example.wealthwatch.data.local.entity.search.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>
    suspend fun insertSearchHistory(query: String)
    suspend fun deleteSearchHistory(query: String)
    suspend fun clearSearchHistory()
}