package com.example.wealthwatch.data.repository

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.data.remote.crypto.CryptoDataSource
import com.example.wealthwatch.domain.model.crypto.Crypto
import com.example.wealthwatch.domain.repository.remote.crypto.CryptoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CryptoRepositoryImpl @Inject constructor(
    private val remote: CryptoDataSource,
) : CryptoRepository {

    override fun getCryptoTickers(): Flow<Resource<List<Crypto>>> =
        remote.getCryptoTickers().map { result ->
                result.mapData { list ->
                    list.sortedByDescending { it.quoteVolume.toDoubleOrNull() ?: 0.0 }
                }
            }.flowOn(Dispatchers.Default)

    override fun tickerUpdate(): Flow<SocketState<List<Crypto>>> = remote.cryptoTickerUpdate()

    override fun searchCrypto(query: String): Flow<Resource<List<Crypto>>> {
        TODO("Not yet implemented")
    }

    override fun getCryptoTop(): Flow<Resource<List<Crypto>>> {
        TODO("Not yet implemented")
    }

    override fun getCryptoGainers(): Flow<Resource<List<Crypto>>> {
        TODO("Not yet implemented")
    }

    override fun getCryptoLosers(): Flow<Resource<List<Crypto>>> {
        TODO("Not yet implemented")
    }

}
