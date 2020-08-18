package com.kfeth.sunshine.utilities

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.Response

fun <ResultType, RequestType> networkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> Response<RequestType>,
    saveFetchResult: suspend (RequestType) -> Unit,
    onFetchFailed: (Throwable) -> Unit = { Unit },
    shouldFetch: (ResultType?) -> Boolean = { true }
) =
    flow {
        emit(Resource.loading(null))
        val data = query().first()
        val flow = if (shouldFetch(data)) {
            emit(Resource.loading(data))
            delay(5000)
            try {
                saveFetchResult((fetch().body()!!))
                query().map { Resource.success(it) }
            } catch (throwable: Throwable) {
                onFetchFailed(throwable)
                query().map { Resource.error(throwable.message ?: "Network error", it) }
            }
        } else {
            query().map { Resource.success(it) }
        }
        emitAll(flow)
    }