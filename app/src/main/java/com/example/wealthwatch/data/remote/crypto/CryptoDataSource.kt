package com.example.wealthwatch.data.remote.crypto

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.asset.MarketAsset
import kotlinx.coroutines.flow.Flow

interface CryptoDataSource {

    // SOCKET
    fun cryptoTickerUpdate(): Flow<SocketState<List<MarketAsset>>>

    // REST
    fun getCryptoTickers(): Flow<Resource<List<MarketAsset>>>
    fun searchCrypto(query: String): Flow<Resource<List<MarketAsset>>>
    fun getCryptoTop(): Flow<Resource<List<MarketAsset>>>
    fun getCryptoGainers(): Flow<Resource<List<MarketAsset>>>
    fun getCryptoLosers(): Flow<Resource<List<MarketAsset>>>
}