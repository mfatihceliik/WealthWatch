package com.example.wealthwatch.data.remote.crypto

import com.example.wealthwatch.data.remote.model.CryptoModel
import com.example.wealthwatch.data.remote.model.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    // ***************************************** CRYPTO *****************************************
    @GET("market/crypto/tickers")
    suspend fun getCryptoTickers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CryptoModel>>>

    @GET("market/crypto/tickers")
    suspend fun searchCrypto(
        @Query("query") search: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CryptoModel>>>

    @GET("market/crypto/top")
    suspend fun getCryptoTop(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CryptoModel>>>

    @GET("market/crypto/gainers")
    suspend fun getCryptoGainers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CryptoModel>>>

    @GET("market/crypto/losers")
    suspend fun getCryptoLosers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<PaginatedResponse<List<CryptoModel>>>

    @GET("market/movers")
    suspend fun getCryptoMovers(
        @Query("period") period: String = "7d"
    ): Response<List<CryptoModel>>
}