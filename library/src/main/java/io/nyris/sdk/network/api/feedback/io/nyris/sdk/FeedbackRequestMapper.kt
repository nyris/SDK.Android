package io.nyris.sdk

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FeedbackRequestMapper @Inject constructor() {
    fun map(event: Event): FeedbackRequest = with(event) {
        FeedbackRequest(
            requestId = this.requestId,
            sessionId = this.sessionId,
            timestamp = timestamp.format(),
            eventType = this.toEventType(),
            data = this.toData()
        )
    }
}

private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"
private fun Long.format() : String = try {
    SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(this)
} catch (ignore: Exception) {
    ""
}

private fun Event.toEventType() : EventTypeDto = when(this) {
    is Event.Click -> EventTypeDto.click
    is Event.Conversion -> EventTypeDto.conversion
    is Event.Feedback -> EventTypeDto.feedback
    is Event.Region -> EventTypeDto.region
    else -> {
        throw IllegalArgumentException("Unsupported event type!")
    }
}

private fun Event.toData() : EventDto = when(this) {
    is Event.Click -> {
        ClickEventDto(
            productIds = this.productIds,
            positions =  this.positions
        )
    }
    is Event.Conversion -> {
        ConversationEventDto(
            productIds = this.productIds,
            positions =  this.positions
        )
    }
    is Event.Feedback ->  {
        FeedbackEventDto(
            success = this.success,
            comment = this.comment
        )
    }
    is Event.Region -> {
        RegionEventDto(
            RectDto(
                x = this.left,
                y = this.top,
                w = this.width,
                h = this.height
            )
        )
    }
    else -> {
        throw IllegalArgumentException("Unsupported event type!")
    }
}