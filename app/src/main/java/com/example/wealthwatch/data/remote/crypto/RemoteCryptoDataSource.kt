package com.example.wealthwatch.data.remote.crypto

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.core.util.SocketState
import com.example.wealthwatch.core.util.mapData
import com.example.wealthwatch.core.util.toSocketState
import com.example.wealthwatch.data.mapper.CryptoMapper
import com.example.wealthwatch.data.remote.BaseDataSource
import com.example.wealthwatch.data.remote.SocketService
import com.example.wealthwatch.domain.model.crypto.Crypto
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteCryptoDataSource @Inject constructor(
    private val socketService: SocketService,
    private val apiService: CryptoApiService,
    private val cryptoMapper: CryptoMapper,
) : BaseDataSource(), CryptoDataSource {

    override fun cryptoTickerUpdate(): Flow<SocketState<List<Crypto>>> = socketService.cryptoTickerUpdate().toSocketState { cryptoMapper(it) }

    override fun getCryptoTickers(): Flow<Resource<List<Crypto>>> =
        getResult { apiService.getCryptoTickers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                cryptoMapper(paginatedResponse.data)
            }
        }

    override fun searchCrypto(query: String): Flow<Resource<List<Crypto>>> =
        getResult { apiService.searchCrypto(query) }.map { resource ->
            resource.mapData { paginatedResponse ->
                cryptoMapper(paginatedResponse.data)
            }
        }

    override fun getCryptoTop(): Flow<Resource<List<Crypto>>> =
        getResult { apiService.getCryptoTop() }.map { resource ->
            resource.mapData { paginatedResponse ->
                cryptoMapper(paginatedResponse.data)
            }
        }

    override fun getCryptoGainers(): Flow<Resource<List<Crypto>>> =
        getResult { apiService.getCryptoGainers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                cryptoMapper(paginatedResponse.data)
            }
        }

    override fun getCryptoLosers(): Flow<Resource<List<Crypto>>> =
        getResult { apiService.getCryptoLosers() }.map { resource ->
            resource.mapData { paginatedResponse ->
                cryptoMapper(paginatedResponse.data)
            }
        }
}