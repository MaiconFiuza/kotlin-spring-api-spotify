package com.br.fiuza.maicon.spotify.controller

import com.br.fiuza.maicon.spotify.models.ArtistResult
import com.br.fiuza.maicon.spotify.models.CreateApiResponse
import com.br.fiuza.maicon.spotify.services.ArtistService
import com.br.fiuza.maicon.spotify.services.SpotifyClient
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/spotify")
class SpotifyController(
        private val spotifyClient: SpotifyClient,
        private val artistService: ArtistService
) {
    @PostMapping
    fun initValidation(): CreateApiResponse {
       val spotify = spotifyClient.authOptions()
        return spotify
    }

    @GetMapping("/artist/{name}")
    fun searchArtist(
            @PathVariable name: String,
            @RequestHeader("Authorization") authorization: String
    ): ArtistResult {
        val artistResult = artistService.getArtistByName(name, authorization)
        return artistResult
    }
}