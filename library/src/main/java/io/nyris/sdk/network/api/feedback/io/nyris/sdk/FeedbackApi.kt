package io.nyris.sdk

import io.reactivex.Single
import okhttp3.ResponseBody

/**
 * FeedbackApi.kt - class that implement FeedbackApi interface.
 *
 * Created by nyris GmbH
 * Copyright © 2022 nyris GmbH. All rights reserved.
 */
internal class FeedbackApi(
    private val feedbackService: FeedbackService,
    private val mapper: FeedbackRequestMapper,
    apiHeader: ApiHeader,
) : Api(apiHeader), IFeedbackApi {
    override fun send(event: Event): Single<ResponseBody> = feedbackService.feedback(
        headers = createDefaultHeadersMap(),
        body = mapper.map(event)
    )
}