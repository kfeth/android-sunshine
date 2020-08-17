package com.kfeth.sunshine.utilities

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.Response

fun <ResultType, RequestType> networkBoundResource(
    dbQuery: () -> Flow<ResultType>,
    fetch: suspend () -> Response<RequestType>,
    saveFetchResult: suspend (RequestType) -> Unit,
    onFetchFailed: (Throwable) -> Unit = { Unit },
    shouldFetch: (ResultType?) -> Boolean = { true }
) =
    flow {
        emit(Resource.loading(null))
        val data = dbQuery().first()
        val flow = if (shouldFetch(data)) {
            emit(Resource.loading(data))
            delay(5000)
            try {
                saveFetchResult((fetch().body()!!))
                dbQuery().map { Resource.success(it) }
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                dbQuery().map { Resource.error(throwable.message ?: "Network error", it) }
            }
        } else {
            dbQuery().map { Resource.success(it) }
        }
        emitAll(flow)
    }