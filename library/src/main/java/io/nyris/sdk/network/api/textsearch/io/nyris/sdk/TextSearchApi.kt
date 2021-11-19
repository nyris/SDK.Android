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

import androidx.annotation.FloatRange
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * TextSearchApi.kt - class that implement ITextSearchApi interface.
 * @see ITextSearchApi
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class TextSearchApi(private val textSearchService: TextSearchService,
                             private var outputFormat: String,
                             private var language: String,
                             private var gson: Gson,
                             schedulerProvider: SdkSchedulerProvider,
                             apiHeader: ApiHeader,
                             endpoints: EndpointBuilder) : Api(schedulerProvider, apiHeader, endpoints), ITextSearchApi {

    private var regroupOptions: RegroupOptions = RegroupOptions()
    private var limit: Int = 20

    /**
     * Init local properties
     */
    private fun reset() {
        regroupOptions.reset()
        limit = 20
    }

    /**
     * {@inheritDoc}
     */
    override fun outputFormat(outputFormat: String): ITextSearchApi {
        this.outputFormat = outputFormat
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun language(language: String): ITextSearchApi {
        this.language = language
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun limit(limit: Int): ITextSearchApi {
        this.limit = limit
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroup(isEnabled: Boolean): ITextSearchApi {
        regroupOptions.enabled = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroup(action: RegroupOptions.() -> Unit): ITextSearchApi {
        action(regroupOptions)
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroupThreshold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): ITextSearchApi {
        regroupOptions.threshold = threshold
        return this
    }

    override fun buildXOptions(): String {
        var xOptions = ""
        if (regroupOptions.enabled) xOptions += "regroup"

        if (regroupOptions.enabled && regroupOptions.threshold != -1F) xOptions += " regroup.threshold=${regroupOptions.threshold}"

        if (limit != 20) xOptions += " limit=$limit"

        reset()
        return xOptions
    }

    /**
     * {@inheritDoc}
     */
    override fun searchOffers(keyword: String): Single<OfferResponseBody> {
        return searchOffers(keyword, OfferResponseBody::class.java)
    }

    /**
     * {@inheritDoc}
     */
    override fun <T : IResponse> searchOffers(keyword: String, clazz: Class<T>): Single<T> {
        val headers = createDefaultHeadersMap()
        val xOptions = buildXOptions()
        headers["Accept"] = outputFormat
        headers["Accept-Language"] = language
        headers["Content-Length"] = keyword.length.toString()
        if (!xOptions.isEmpty())
            headers["X-Options"] = xOptions
        val body = RequestBody.create("text/plain; charset=utf-8".toMediaTypeOrNull(), keyword)

        val obs1 = textSearchService.searchOffers(endpoints.textSearchUrl, headers, body)

        return convertResponseBodyBasedOnType(keyword, obs1, clazz, gson)
    }
}