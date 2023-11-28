package com.br.fiuza.maicon.spotify.services

import okhttp3.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit


@Component
class OkHttpClient {

    private val connectionPool = ConnectionPool(
            maxIdleConnections = 5,
            keepAliveDuration = 60L,
            timeUnit = TimeUnit.SECONDS
    )

    val httpClient = OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .retryOnConnectionFailure(false)
            .build()

    fun performGetRequest(url: String, authorization: String): Result<ResponseBody> {
        val request = Request.Builder()
                .header("Authorization", authorization)
                .url(url)
                .build()

        return httpClient
                .newCall(request)
                .runCatching { execute() }
                .mapCatching { response ->
                    when (response.isSuccessful) {
                        true -> response.body
                        false -> throw UnknownError("Deu Ruim no get")
                    }
                }
    }

    fun initPostRequest(url: String, body: String): String {

        val request = Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url(url)
                .post(body.toRequestBody())
                .build()

        return httpClient
                .newCall(request)
                .runCatching { execute() }
                .mapCatching { response ->
                    when (response.isSuccessful) {
                        true -> response.body.string()
                        false -> throw UnknownError("Deu ruim no post inicial")
                    }
                }.getOrThrow()
    }
}