package io.nyris.sdk

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
