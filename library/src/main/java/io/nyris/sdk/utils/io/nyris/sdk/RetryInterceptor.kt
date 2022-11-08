package io.nyris.sdk

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val retryCount: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var exception: IOException? = null
        var tryCount = 0
        // retry the request
        while (tryCount < retryCount && (response == null || !response.isSuccessful)) {
            try {
                response = chain.proceed(request)
            } catch (e: IOException) {
                exception = e
            } finally {
                tryCount++
            }
        }
        // throw last exception
        if (response == null && exception != null) {
            throw exception
        }
        // otherwise just pass the original response on
        return response!!
    }
}