package com.github.thibseisel.sfyxplor

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }

        get("/html-freemarker") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }
    }
}