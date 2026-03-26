package com.example.wealthwatch.domain.repository.remote.crypto

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.data.remote.model.CurrencyModel
import com.example.wealthwatch.domain.model.crypto.Crypto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CryptoRepository {

    // SOCKET
    fun tickerUpdate(): Flow<SocketState<List<Crypto>>>

    // REMOTE
    fun getCryptoTickers(): Flow<Resource<List<Crypto>>> // REST
    fun searchCrypto(query: String): Flow<Resource<List<Crypto>>>
    fun getCryptoTop(): Flow<Resource<List<Crypto>>>
    fun getCryptoGainers(): Flow<Resource<List<Crypto>>>
    fun getCryptoLosers(): Flow<Resource<List<Crypto>>>
    //fun getCryptoMovers(): Flow<Resource<List<Crypto>>>

}
