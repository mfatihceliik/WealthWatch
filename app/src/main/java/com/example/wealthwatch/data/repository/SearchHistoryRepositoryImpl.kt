package com.example.wealthwatch.data.repository

import com.example.wealthwatch.data.local.LocalDataSource
import com.example.wealthwatch.domain.repository.local.search_history.SearchHistoryRepository
import jakarta.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val local: LocalDataSource
): SearchHistoryRepository {

    override fun getSearchHistory() = local.getSearchHistory()
    override suspend fun insertSearchHistory(query: String) = local.insertSearchHistory(query)
    override suspend fun deleteSearchHistory(query: String) = local.deleteSearchHistory(query)
    override suspend fun clearSearchHistory() = local.clearSearchHistory()
}