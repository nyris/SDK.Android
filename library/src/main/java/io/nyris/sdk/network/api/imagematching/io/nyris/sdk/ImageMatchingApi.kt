/*
 * Copyright (C) 2018 nyris GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nyris.sdk

import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.util.Base64
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * ImageMatchingApi.kt - class that implement IImageMatchingApi interface.
 * @see IImageMatchingApi
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class ImageMatchingApi(private val imageMatchingService: ImageMatchingService,
                                private var outputFormat: String,
                                private var language: String,
                                private var gson: Gson,
                                schedulerProvider: SdkSchedulerProvider,
                                apiHeader: ApiHeader,
                                endpoints: EndpointBuilder) : Api(schedulerProvider, apiHeader, endpoints), IImageMatchingApi {
    private var enableExact: Boolean = true
    private var enableSimilarity: Boolean = true
    private var similarityLimit: Int = -1
    private var similarityThreshold: Float = -1F
    private var enableOcr: Boolean = true
    private var enableRegroup: Boolean = false
    private var regroupThreshold: Float = -1F
    private var limit: Int = 20
    private var enableRecommendation: Boolean = false

    /**
     * Init local properties
     */
    private fun `init`() {
        enableExact = true
        enableSimilarity = true
        similarityLimit = -1
        similarityThreshold = -1F
        enableOcr = true
        enableRegroup = false
        regroupThreshold = -1F
        limit = 20
        enableRecommendation = false
    }

    /**
     * {@inheritDoc}
     */
    override fun outputFormat(outputFormat: String): ImageMatchingApi {
        this.outputFormat = outputFormat
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun language(language: String): ImageMatchingApi {
        this.language = language
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun exact(isEnabled: Boolean): IImageMatchingApi {
        enableExact = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun similarity(isEnabled: Boolean): IImageMatchingApi {
        enableSimilarity = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun similarityLimit(@IntRange(from = 1, to = 100) limit: Int): IImageMatchingApi {
        similarityLimit = limit
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun similarityThreshold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): IImageMatchingApi {
        similarityThreshold = threshold
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun ocr(isEnabled: Boolean): IImageMatchingApi {
        enableOcr = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun limit(limit: Int): IImageMatchingApi {
        this.limit = limit
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroup(isEnabled: Boolean): IImageMatchingApi {
        enableRegroup = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroupThreshold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): IImageMatchingApi {
        regroupThreshold = threshold
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun recommendations(isEnabled: Boolean): IImageMatchingApi {
        enableRecommendation = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun buildXOptions(): String {
        var xOptions = ""

        if (enableExact && xOptions.isEmpty()) xOptions = "exact"

        if (enableSimilarity && xOptions.isEmpty()) xOptions = "similarity"
        else
            if (enableSimilarity) xOptions += " +similarity"

        if (enableOcr && xOptions.isEmpty()) xOptions = "ocr"
        else
            if (enableOcr) xOptions += " +ocr"

        if (enableSimilarity && similarityLimit != -1) xOptions += " similarity.limit=$similarityLimit"

        if (enableSimilarity && similarityThreshold != -1F) xOptions += " similarity.threshold=$similarityThreshold"

        if (enableRegroup) xOptions += " +regroup"

        if (enableRegroup && regroupThreshold != -1F) xOptions += " regroup.threshold=$regroupThreshold"

        if (limit != 20) xOptions += " limit=$limit"

        if (enableRecommendation) xOptions += " +recommendations"

        init()
        return xOptions
    }

    /**
     * Build Headers for image matching endpoint
     */
    private fun buildHeaders(contentSize: Int): HashMap<String, String> {
        val headers = createDefaultHeadersMap()
        headers["Accept"] = outputFormat
        headers["Accept-Language"] = language
        headers["Content-Length"] = contentSize.toString()
        headers["X-Options"] = buildXOptions()
        return headers
    }

    /**
     * {@inheritDoc}
     */
    override fun match(image: ByteArray): Single<OfferResponseBody> {
        return match(image, OfferResponseBody::class.java)
    }

    /**
     * {@inheritDoc}
     */
    override fun match(image: FloatArray): Single<OfferResponseBody> {
        return match(image, OfferResponseBody::class.java)
    }

    private fun encodeFloatArray(floatArray: FloatArray): String {
        val buf = ByteBuffer
                .allocate(java.lang.Float.SIZE / java.lang.Byte.SIZE * floatArray.size)
                .order(ByteOrder.LITTLE_ENDIAN)
        buf.asFloatBuffer().put(floatArray)
        return Base64.encodeToString(buf.array(), Base64.NO_WRAP)
    }

    /**
     * {@inheritDoc}
     */
    override fun <T : IResponse> match(image: ByteArray, clazz: Class<T>): Single<T> {
        if (enableRecommendation && !ternaryOr(enableExact, enableSimilarity, enableOcr)) {
            val exception = Exception("To use the recommendation feature, you need to enable one of this stages : exact, similarity, ocr.")
            return Single.error<T>(exception)
        }

        if (enableRegroup && !ternaryOr(enableExact, enableSimilarity, enableOcr)) {
            val exception = Exception("To use the regrouping feature, you need to enable one of this stages : exact, similarity, ocr.")
            return Single.error<T>(exception)
        }

        val headers = buildHeaders(image.size)
        val body = RequestBody.create(MediaType.parse("image/jpg"), image)
        val typeOfferResponse = OfferResponse::class.java

        return if (clazz.name == typeOfferResponse.name) {
            val obs1 = imageMatchingService.matchAndGetRequestId(endpoints.imageMatchingUrl, headers, body)
            convertResponseBasedOnType(image, obs1)
        } else {
            val obs1 = imageMatchingService.match(endpoints.imageMatchingUrl, headers, body)
            convertResponseBodyBasedOnType(image, obs1, clazz, gson)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun <T : IResponse> match(image: FloatArray, clazz: Class<T>): Single<T> {
        if (enableRecommendation && !ternaryOr(enableExact, enableSimilarity, enableOcr)) {
            val exception = Exception("To use the recommendation feature, you need to enable one of this stages : exact, similarity.")
            return Single.error<T>(exception)
        }

        if (enableRegroup && !ternaryOr(enableExact, enableSimilarity, enableOcr)) {
            val exception = Exception("To use the regrouping feature, you need to enable one of this stages : exact, similarity, ocr.")
            return Single.error<T>(exception)
        }

        val b64 = encodeFloatArray(image)
        val json = "{\"b64\":\"$b64\"}"
        val headers = buildHeaders(json.length)
        val body = RequestBody.create(MediaType.parse("application/json"), json)
        val typeOfferResponse = OfferResponse::class.java

        return if (clazz.name == typeOfferResponse.name) {
            val obs1 = imageMatchingService.matchAndGetRequestId(endpoints.imageMatchingUrl2, headers, body)
            convertResponseBasedOnType(image, obs1)
        } else {
            val obs1 = imageMatchingService.match(endpoints.imageMatchingUrl2, headers, body)
            convertResponseBodyBasedOnType(image, obs1, clazz, gson)
        }
    }
}