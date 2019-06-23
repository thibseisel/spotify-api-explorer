package com.github.thibseisel.sfyxplor

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

private const val TEST_CLIENT_KEY = "Y2xpZW50OmtleQ=="

class SpotifyApiImplTest {

    @Test
    fun givenNoAuthorization_whenSearchingArtists_thenPerformAuthenticationFirst() = runBlockingTest {
        val http = HttpClient(MockEngine, mockSpotifyApiConfig)
        val api = SpotifyApiImpl(http, TEST_CLIENT_KEY, accessToken = null)

        api.searchArtists("")
    }
}