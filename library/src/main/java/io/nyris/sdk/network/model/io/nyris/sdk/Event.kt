package io.nyris.sdk

import androidx.annotation.FloatRange
import androidx.annotation.Keep

@Keep
sealed class Event(
    internal val requestId: String,
    internal val sessionId: String,
    internal val timestamp: Long
) {
    @Keep
    class Click(
        requestId: String,
        sessionId: String,
        val positions: List<Int>,
        val productIds: List<String>
    ) : Event(requestId, sessionId, System.currentTimeMillis())

    @Keep
    class Conversion(
        requestId: String,
        sessionId: String,
        val positions: List<Int>,
        val productIds: List<String>
    ) : Event(requestId, sessionId, System.currentTimeMillis())

    @Keep
    class Feedback(
        requestId: String,
        sessionId: String,
        val success: Boolean,
        val comment: String? = null
    ) : Event(requestId, sessionId, System.currentTimeMillis())

    @Keep
    class Region(
        requestId: String,
        sessionId: String,
        @FloatRange(from = 0.0, to = 1.0)
        val left: Float,
        @FloatRange(from = 0.0, to = 1.0)
        val top: Float,
        @FloatRange(from = 0.0, to = 1.0)
        val width: Float,
        @FloatRange(from = 0.0, to = 1.0)
        val height: Float
    ) : Event(requestId, sessionId, System.currentTimeMillis())
}

