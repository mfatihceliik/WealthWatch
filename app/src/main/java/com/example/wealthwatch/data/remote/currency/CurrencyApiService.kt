package com.example.wealthwatch.data.remote.currency

import com.example.wealthwatch.data.remote.model.CurrencyModel
import com.example.wealthwatch.data.remote.model.CurrencyRateResponse
import com.example.wealthwatch.data.remote.model.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("market/currency-rates")
    suspend fun getCurrencies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CurrencyModel>>>

    @GET("currency/rates")
    suspend fun getCurrencyRates(): Response<CurrencyRateResponse>

    @GET("market/currency-rates/search")
    suspend fun searchCurrencies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CurrencyModel>>>

    @GET("market/currency-rates/gainers")
    suspend fun getCurrencyGainers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CurrencyModel>>>

    @GET("market/currency-rates/losers")
    suspend fun getCurrencyLosers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CurrencyModel>>>
}