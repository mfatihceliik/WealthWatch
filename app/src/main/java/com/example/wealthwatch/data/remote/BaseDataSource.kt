package com.example.wealthwatch.data.remote

import com.example.wealthwatch.core.util.Resource
import com.example.wealthwatch.presentation.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseDataSource {

    companion object {
        private val TAG = this::class.java.simpleName
    }
    protected fun <T> getResult(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.Success(body, response.message()))
                } else {
                    emit(Resource.Error("Response body is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                emit(Resource.Error("${response.code()} - $errorBody"))
                log(TAG, errorBody)
            }
        } catch (e: java.net.UnknownHostException) {
            emit(
                Resource.Error(
                    "No internet connection. Please check your network."
                )
            )
            log(TAG, e.message)
        } catch (exception: Exception) {
            emit(Resource.Error(exception.message ?: exception.toString()))
            log(TAG, exception.message ?: exception.toString())
        }
    }.flowOn(Dispatchers.IO)
}
