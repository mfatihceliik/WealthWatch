package com.example.wealthwatch.data.remote.commodity

import com.example.wealthwatch.data.remote.model.CommoditiesModel
import com.example.wealthwatch.data.remote.model.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommodityApiService {

    @GET("market/commodities")
    suspend fun getCommodities(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CommoditiesModel>>>

    @GET("market/commodities/search")
    suspend fun searchCommodities(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CommoditiesModel>>>

    @GET("market/commodities/gainers")
    suspend fun getCommoditiesGainers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CommoditiesModel>>>

    @GET("market/commodities/losers")
    suspend fun getCommoditiesLosers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CommoditiesModel>>>
}