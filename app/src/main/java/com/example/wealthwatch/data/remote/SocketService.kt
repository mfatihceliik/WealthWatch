package com.example.wealthwatch.data.remote

import com.example.wealthwatch.data.remote.model.ExchangeModel
import com.example.wealthwatch.data.remote.model.CryptoModel
import com.example.wealthwatch.data.remote.model.StockModel
import kotlinx.coroutines.flow.Flow

interface SocketService {
    fun cryptoTickerUpdate(): Flow<List<CryptoModel>>
    fun currencyTickerUpdate(): Flow<List<ExchangeModel>>
    fun stockTickerUpdate(): Flow<List<StockModel>>
}