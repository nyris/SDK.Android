package io.nyris.sdk

import io.reactivex.Single
import okhttp3.ResponseBody

/**
 * IFeedbackApi.kt - interface for sending user feedback.
 *
 * Created by nyris GmbH
 * Copyright © 2022 nyris GmbH. All rights reserved.
 */
interface IFeedbackApi {

    /**
     * Sent a feedback request
     * @param request the body request @see FeedbackRequest
     * @return the Single ResponseBody
     */
    fun send(event: Event): Single<ResponseBody>
}