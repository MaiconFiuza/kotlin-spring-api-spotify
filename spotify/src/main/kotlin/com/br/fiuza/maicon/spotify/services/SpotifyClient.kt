package com.br.fiuza.maicon.spotify.services

import com.br.fiuza.maicon.spotify.models.CreateApiResponse
import com.br.fiuza.maicon.spotify.models.SpotifyArtist
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.net.URLEncoder

@Component
class SpotifyClient(
        private val objectMapper: ObjectMapper
): OkHttpClient() {

    fun authOptions(): CreateApiResponse {

        val encodedClientId = URLEncoder.encode("yourCliendId", "UTF-8")
        val encodedClientSecret = URLEncoder.encode("yourClientSecret", "UTF-8")
        val composedUrl = "https://accounts.spotify.com/api/token?grant_type=client_credentials&client_id=${encodedClientId}&client_secret=${encodedClientSecret}"

        val result = initPostRequest(composedUrl, "")

        return objectMapper.readValue(result, CreateApiResponse::class.java)
    }

    fun search(name: String, authorization: String): SpotifyArtist {
        val updatedAuthorization = "Bearer $authorization"
        val composedUrl = "https://api.spotify.com/v1/search?q=${name}&type=artist"

        val result = performGetRequest(composedUrl, updatedAuthorization).getOrThrow()

        return objectMapper.readValue(result.string(), SpotifyArtist::class.java)
    }

}