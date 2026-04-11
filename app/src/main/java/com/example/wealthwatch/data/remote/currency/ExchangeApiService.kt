package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.data.remote.model.ExchangeModel
import com.example.wealthwatch.data.remote.model.ExchangeRateResponse
import com.example.wealthwatch.data.remote.model.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApiService {
    @GET("market/currency-rates")
    suspend fun getExchanges(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<ExchangeModel>>>

    @GET("currency/rates")
    suspend fun getExchangeRates(): Response<ExchangeRateResponse>

    @GET("market/currency-rates/search")
    suspend fun searchExchange(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<ExchangeModel>>>

    @GET("market/currency-rates/gainers")
    suspend fun getExchangeGainers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<ExchangeModel>>>

    @GET("market/currency-rates/losers")
    suspend fun getExchangeLosers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<ExchangeModel>>>
}