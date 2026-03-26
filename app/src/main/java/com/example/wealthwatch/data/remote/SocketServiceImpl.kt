package com.example.wealthwatch.data.remote

import android.util.Log
import com.example.wealthwatch.data.remote.model.CurrencyModel
import com.example.wealthwatch.data.remote.model.CryptoModel
import com.example.wealthwatch.data.remote.model.StockModel
import com.example.wealthwatch.di.ApplicationScope
import com.example.wealthwatch.di.DispatcherDefault
import io.socket.client.Socket
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class SocketServiceImpl @Inject constructor(
    private val socket: Socket,
    private val json: Json,
    @param:DispatcherDefault private val dispatcherDefault: CoroutineDispatcher,
    @param:ApplicationScope private val scope: CoroutineScope
) : SocketService {

    init {
        setupSocketListeners()
        connectSocket()
    }

    private fun setupSocketListeners() {
        socket.on(Socket.EVENT_CONNECT) {
            Log.d("MarketSocket", "Connected to Socket.IO")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d("MarketSocket", "Disconnected from Socket.IO")
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.e("MarketSocket", "Connect Error: ${args.firstOrNull()}")
            // Optional: Implement retry logic if not built-in (Socket.IO client has reconnection usually)
        }
    }

    private fun connectSocket() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    private val tickerFlow: SharedFlow<List<CryptoModel>> = callbackFlow {
        val listener: (Array<Any>) -> Unit = { args ->
            val data = args.firstOrNull()
            if (data != null) {
                // Direct to Default dispatcher for CPU bound parsing
                launch(dispatcherDefault) {
                    try {
                        val jsonString = data.toString()
                        val model = json.decodeFromString<List<CryptoModel>>(jsonString)
                        trySend(model)
                    } catch (e: Exception) {
                        Log.e("MarketSocket", "Ticker Parse Error: ${e.message}")
                    }
                }
            }
        }

        socket.on("tickerUpdate", listener)

        awaitClose {
            socket.off("tickerUpdate", listener)
            // Do NOT disconnect socket here as it might be used by other flows (Currency)
            // or should be kept alive for the session.
        }
    }.conflate().shareIn(
        scope,
        started = SharingStarted.Lazily,
        replay = 1
    )

    override fun cryptoTickerUpdate(): Flow<List<CryptoModel>> = tickerFlow

    private val currencyFlow: SharedFlow<List<CurrencyModel>> = callbackFlow {
        val listener: (Array<Any>) -> Unit = { args ->
            val data = args.firstOrNull()
            if (data != null) {
                // Direct to Default dispatcher for CPU bound parsing
                launch(dispatcherDefault) {
                    try {
                        val jsonString = data.toString()
                        val currencies = json.decodeFromString<List<CurrencyModel>>(jsonString)
                        trySend(currencies)
                    } catch (e: Exception) {
                        Log.e("MarketSocket", "Currency Parse Error: ${e.message}")
                    }
                }
            }
        }

        socket.on("currencyUpdate", listener)

        awaitClose {
            socket.off("currencyUpdate", listener)
        }
    }.conflate().shareIn(
        scope,
        started = SharingStarted.Lazily,
        replay = 1
    )

    override fun currencyTickerUpdate(): Flow<List<CurrencyModel>> = currencyFlow

    private val stockFlow: SharedFlow<List<StockModel>> = callbackFlow {
        val listener: (Array<Any>) -> Unit = { args ->
            val data = args.firstOrNull()
            if (data != null) {
                // Direct to Default dispatcher for CPU bound parsing
                launch(dispatcherDefault) {
                    try {
                        val jsonString = data.toString()
                        val stocks = json.decodeFromString<List<StockModel>>(jsonString)
                        trySend(stocks)
                    } catch (e: Exception) {
                        Log.e("MarketSocket", "Stock Parse Error: ${e.message}")
                    }
                }
            }
        }

        socket.on("stockUpdate", listener)

        awaitClose {
            socket.off("stockUpdate", listener)
        }
    }.conflate().shareIn(
        scope,
        started = SharingStarted.Lazily,
        replay = 1
    )

    override fun stockTickerUpdate(): Flow<List<StockModel>> = stockFlow
}