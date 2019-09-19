package com.github.thibseisel.api.spotify

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import org.intellij.lang.annotations.*

/**
 * Create an HTTP response with the provided [json] as the content body.
 *
 * @param json The content of the response, formatted as a valid JSON string.
 * @param status The status code of the HTTP response. Defaults to 200 (OK).
 */
internal fun respondJson(@Language("JSON") json: String, status: HttpStatusCode = HttpStatusCode.OK) =
    respond(
        json,
        status,
        headersOf(
            HttpHeaders.ContentType,
            ContentType.Application.Json.toString()
        )
    )