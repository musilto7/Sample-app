package cz.musilto5.myflickerapp.data.core

import cz.musilto5.myflickerapp.data.api.service.FlickerApi
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType

fun createFlickerApi(httpClientEngine: HttpClientEngine): FlickerApi {
    val config: (HttpClientConfig<*>.() -> Unit) = {
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
        install(Logging) {
            level = LogLevel.BODY
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
    }
    return FlickerApi(httpClientEngine = httpClientEngine, httpClientConfig = config)
}
