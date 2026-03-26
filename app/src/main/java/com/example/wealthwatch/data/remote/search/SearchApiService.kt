package com.example.wealthwatch.data.remote.search

import com.example.wealthwatch.data.remote.model.SearchInitialResponse
import retrofit2.Response
import retrofit2.http.GET

interface SearchApiService {
    @GET("search/initial")
    suspend fun getSearchInitialData(): Response<SearchInitialResponse>

    @GET("search/query")
    suspend fun searchAssets(@retrofit2.http.Query("q") query: String): Response<List<com.example.wealthwatch.data.remote.model.AssetModel>>
}
