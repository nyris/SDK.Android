package io.nyris.sdk

import androidx.annotation.FloatRange
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

internal enum class EventTypeDto { click, conversion, feedback, region }


internal class FeedbackRequest(
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("event")
    val eventType: EventTypeDto,
    @SerializedName("data")
    val data: EventDto
)


internal sealed class EventDto

internal class ClickEventDto(
    @SerializedName("positions")
    val positions: List<Int>,
    @SerializedName("product_ids")
    val productIds: List<String>
) : EventDto()

internal class ConversationEventDto(
    @SerializedName("positions")
    val positions: List<Int>,
    @SerializedName("product_ids")
    val productIds: List<String>
) : EventDto()

internal class FeedbackEventDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("comment")
    val comment: String
) : EventDto()

internal class RegionEventDto(
    @SerializedName("rect")
    val rect: RectDto
) : EventDto()

internal class RectDto(
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float,
    @SerializedName("w")
    val w: Float,
    @SerializedName("h")
    val h: Float
)

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
        val comment: String = ""
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

