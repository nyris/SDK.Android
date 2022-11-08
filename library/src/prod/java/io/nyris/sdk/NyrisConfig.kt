package io.nyris.sdk

class NyrisConfig @JvmOverloads constructor(
    var isDebug: Boolean = false,
    var scheme: String = SCHEME,
    var hostUrl: String = HOST_URL,
    var defaultOutputFormat: String = DEFAULT_OUTPUT_FORMAT,
    var defaultLanguage: String = DEFAULT_LANGUAGE,
    var networkConnectionTimeOut: Long = NETWORK_CONNECTION_TIMEOUT,
    var httpRetryCount: Int = HTTP_RETRY_COUNT,
    internal val apiVersion: String = API_VERSION,
) {
    internal companion object {
        const val SCHEME = "https"
        const val HOST_URL = "api.nyris.io"
        const val API_VERSION = "v1"
        const val DEFAULT_OUTPUT_FORMAT = "application/offers.complete+json"
        const val DEFAULT_LANGUAGE = "*"
        const val NETWORK_CONNECTION_TIMEOUT = 30L
        const val HTTP_RETRY_COUNT = 3
    }
}