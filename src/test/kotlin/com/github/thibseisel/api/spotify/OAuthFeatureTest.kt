package com.github.thibseisel.api.spotify

import com.google.gson.annotations.*
import io.kotlintest.*
import io.kotlintest.matchers.types.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.test.*
import org.intellij.lang.annotations.*
import org.junit.*

private const val PERSON_API_URL = "https://api.example.com/persons/42"

@Language("JSON")
private const val SAMPLE_JSON_RESPONSE = """{
    "firstname": "John",
    "lastname": "Doe"
}"""

@Language("JSON")
private const val FRESH_TOKEN = """{
    "access_token": "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
    "token_type": "bearer",
    "expires_in": 3600
}"""

private class Person(
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String
)

class OAuthFeatureTest {

    @Test
    fun `When unauthenticated then attempt authentication before request`() = runBlockingTest {
        val client = HttpClient(MockEngine) {
            withOAuth()
            withJson()

            engine {
                addHandler { authRequest ->
                    with(authRequest) {
                        method shouldBe HttpMethod.Post
                        url shouldBe Url("https://auth.example.com/token")
                        headers[HttpHeaders.Authorization] shouldBe "Basic Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ="
                        body.shouldBeTypeOf<FormDataContent> {
                            it.formData["grant_type"] shouldBe "client_credentials"
                        }
                    }

                    respondJson(FRESH_TOKEN)
                }

                addHandler { baseRequest ->
                    with(baseRequest) {
                        method shouldBe HttpMethod.Get
                        url.toString() shouldBe PERSON_API_URL
                        headers[HttpHeaders.Authorization] shouldBe "Bearer MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3"
                    }

                    respondJson(SAMPLE_JSON_RESPONSE)
                }
            }
        }

        val person = client.get<Person>(PERSON_API_URL)
        person.firstName shouldBe "John"
        person.lastName shouldBe "Doe"
    }

    @Test
    fun `When authenticated then directly perform request with authentication`() = runBlockingTest {
        val client = HttpClient(MockEngine) {
            withJson()

            install(OAuth) {
                authServerUrl = Url("https://auth.example.com/token")
                token = OAuthToken("MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3", "bearer", 3600)
            }

            engine {
                addHandler {
                    it.method shouldBe HttpMethod.Get
                    it.url.toString() shouldBe PERSON_API_URL
                    it.headers[HttpHeaders.Authorization] shouldBe "Bearer MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3"

                    respondJson(SAMPLE_JSON_RESPONSE)
                }
            }
        }

        val person = client.get<Person>(PERSON_API_URL)
        person.firstName shouldBe "John"
        person.lastName shouldBe "Doe"
    }

    @Test
    fun `When token is expired then renew authentication`() = runBlockingTest {
        val client = HttpClient(MockEngine) {
            withJson()

            install(OAuth) {
                authServerUrl = Url("https://auth.example.com/token")
            }
        }
    }

    private fun HttpClientConfig<*>.withOAuth() {
        install(OAuth) {
            authServerUrl = Url("https://auth.example.com/token")
            clientId = TEST_CLIENT_ID
            clientSecret = TEST_CLIENT_SECRET
        }
    }

    private fun HttpClientConfig<*>.withJson() {
        Json { serializer = GsonSerializer() }
    }
}