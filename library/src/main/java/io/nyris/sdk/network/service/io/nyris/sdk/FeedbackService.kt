package io.nyris.sdk

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

/**
 * FeedbackService.kt - http interface of feedback service
 *
 * Created by nyris GmbH
 * Copyright © 2022 nyris GmbH. All rights reserved.
 */
internal interface FeedbackService {
    /**
     * Http Post feedback request
     *
     * @param body the request body
     * @return the Single Response Body
     */
    @POST("feedback/v1")
    fun feedback(
        @HeaderMap headers: Map<String, String>,
        @Body body: FeedbackRequest
    ): Single<ResponseBody>
}