package com.github.thibseisel.sfyxplor

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.freemarker.respondTemplate
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.util.getOrFail

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    val spotifyApi = FakeSpotifyApi()

    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }

        get("/search") {
            // Search results.
            val query = call.parameters["q"].orEmpty()
            val type = call.parameters["type"]

            val results = spotifyApi.search(query)
            call.respond(FreeMarkerContent("search.ftl", mapOf(
                "query" to query,
                "results" to results
            )))
        }

        get("/artists/{id}") {
            val artistId = call.parameters.getOrFail("id")
            val albums = spotifyApi.findArtistAlbums(artistId)

            call.respondTemplate("albums.ftl", mapOf("albums" to albums))
        }

        get("/albums/{id}") {
            val albumId = call.parameters.getOrFail("id")
            val tracks = spotifyApi.findAlbumTracks(albumId)

            call.respondTemplate("tracks.ftl", mapOf("tracks" to tracks))
        }

        get("/tracks/{id}") {
            val trackId = call.parameters.getOrFail("id")
            val trackFeatures = spotifyApi.getTrackFeatures(trackId)

            call.respondTemplate("features.ftl", mapOf("features" to trackFeatures))
        }
    }
}