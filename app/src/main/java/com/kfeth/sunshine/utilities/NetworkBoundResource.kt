package com.kfeth.sunshine.utilities

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

            try {
                saveFetchResult((fetch().body()!!))
                query().map { Resource.success(it) }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                onFetchFailed(throwable)
                query().map { Resource.error(throwable.message ?: "Network error", it) }
            }
        } else {
            query().map { Resource.success(it) }
        }
        emitAll(flow)
    }.flowOn(Dispatchers.IO)