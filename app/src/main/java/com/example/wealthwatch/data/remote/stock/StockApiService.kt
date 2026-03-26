package com.example.wealthwatch.data.remote.stock

import com.example.wealthwatch.data.remote.model.PaginatedResponse
import com.example.wealthwatch.data.remote.model.StockModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApiService {
    @GET("market/stocks/us")
    suspend fun getUsStocks(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/tr")
    suspend fun getBistStocks(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/tr/gainers")
    suspend fun getBistGainers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/tr/losers")
    suspend fun getBistLosers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/us/gainers")
    suspend fun getUsGainers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/us/losers")
    suspend fun getUsLosers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/search")
    suspend fun searchStocks(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/us/search")
    suspend fun searchUsStocks(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>

    @GET("market/stocks/tr/search")
    suspend fun searchBistStocks(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<StockModel>>>
}