package com.github.thibseisel.api.spotify

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.io.*
import java.util.*

class OAuth(
    private val authServerUrl: Url,
    private val base64Key: String,
    private var token: OAuthToken?
) {
    companion object : HttpClientFeature<Config, OAuth> {
        override val key: AttributeKey<OAuth> = AttributeKey("Client-OAuth")

        override fun prepare(block: Config.() -> Unit): OAuth {
            val config = Config().apply(block)
            val authServerUrl = config.authServerUrl ?: URLBuilder().build()
            val base64Key = forgeBase64Key(config.clientId.orEmpty(), config.clientSecret.orEmpty())
            return OAuth(authServerUrl, base64Key, config.token)
        }

        private fun forgeBase64Key(clientKey: String, clientSecret: String): String {
            val encoder = Base64.getEncoder()
            return encoder.encodeToString("$clientKey:$clientSecret".toByteArray())
        }

        override fun install(feature: OAuth, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                if (feature.token == null) {
                    val authRequest = HttpRequestBuilder().apply {
                        url.takeFrom(feature.authServerUrl)
                        method = HttpMethod.Post
                        headers[HttpHeaders.Authorization] = "Basic ${feature.base64Key}"
                        body = FormDataContent(Parameters.build {
                            append("grant_type", "client_credentials")
                        })
                    }

                    val authCall = scope.sendPipeline.execute(authRequest, authRequest.body) as HttpClientCall
                    val renewedToken = authCall.receive<OAuthToken>()
                    feature.token = renewedToken
                }
            }

            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                feature.token?.let { validToken ->
                    context.headers[HttpHeaders.Authorization] = "Bearer ${validToken.token}"
                }
            }
        }
    }

    class Config {
        internal var authServerUrl: Url? = null
        internal var token: OAuthToken? = null
        var clientId: String? = null
        var clientSecret: String? = null

        fun authorizationUrl(block: URLBuilder.() -> Unit) {
            authServerUrl = URLBuilder().apply(block).build()
        }
    }
}