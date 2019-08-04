package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.*
import freemarker.cache.*
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.coroutines.*
import org.koin.core.qualifier.*
import org.koin.dsl.*
import org.koin.ktor.ext.*
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val koinModule = module {
        single {
            SpotifyApiClient(OkHttp.create(), "SpotifyApiExplorer/1.0.0 OkHttp/3")
        }

        single {
            val clientId = System.getenv("SPOTIFY_CLIENT_ID")
            val clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET")
            SpotifySource(clientId, clientSecret, get())
        }
    }

    install(Koin) {
        modules(koinModule)
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        val spotify by inject<SpotifySource>()

        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }

        get("/search") {
            // Search results.
            val query = call.parameters["q"].orEmpty()
            val type = call.parameters["type"]

            val results = spotify.search(query, type)

            call.respondTemplate("search.ftl", mapOf(
                "query" to query,
                "results" to results.artists.items
            ))
        }

        get("/artists/{id}") {
            val artistId = call.parameters.getOrFail("id")
            val albums = spotify.getArtistAlbums(artistId)

            call.respondTemplate("albums.ftl", mapOf("albums" to albums.items))
        }

        get("/albums/{id}") {
            val albumId = call.parameters.getOrFail("id")
            val tracks = spotify.getAlbumTracks(albumId)

            call.respondTemplate("tracks.ftl", mapOf("tracks" to tracks.items))
        }

        get("/tracks/{id}") {
            val trackId = call.parameters.getOrFail("id")
            val trackFeatures = spotify.getTrackFeatures(trackId)

            call.respondTemplate("features.ftl", mapOf("features" to trackFeatures))
        }
    }
}