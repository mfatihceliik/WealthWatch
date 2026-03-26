package com.example.wealthwatch.data.remote.market

import com.example.wealthwatch.data.remote.model.MarketOverviewResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarketApiService {

    @GET("market/overview")
    suspend fun getMarketOverview(): Response<MarketOverviewResponse>
}