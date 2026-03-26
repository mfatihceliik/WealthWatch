package com.example.wealthwatch.data.remote.crypto

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.domain.model.commodities.Commodities
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.model.currency.Currency
import com.example.wealthwatch.domain.model.stock.Stock
import kotlinx.coroutines.flow.Flow

interface CryptoDataSource {

    // SOCKET
    fun cryptoTickerUpdate(): Flow<SocketState<List<Crypto>>>

    // REST
    fun getCryptoTickers(): Flow<Resource<List<Crypto>>>
    fun searchCrypto(query: String): Flow<Resource<List<Crypto>>>
    fun getCryptoTop(): Flow<Resource<List<Crypto>>>
    fun getCryptoGainers(): Flow<Resource<List<Crypto>>>
    fun getCryptoLosers(): Flow<Resource<List<Crypto>>>
    //fun getCryptoMovers(): Flow<Resource<List<Crypto>>>
}