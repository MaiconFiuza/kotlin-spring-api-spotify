package com.br.fiuza.maicon.spotify.services

import com.br.fiuza.maicon.spotify.models.ArtistResult
import org.springframework.stereotype.Service

@Service
class ArtistService(
        private val spotifyClient: SpotifyClient
) {

    fun getArtistByName(name: String, authorization: String): ArtistResult {

        val artist = spotifyClient.search(name,authorization)
        val result = artist.artists.items.first()

        return ArtistResult(
                id = result.id,
                name = result.name,
                image = result.images.first().url
        )
    }
}