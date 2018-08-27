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

import com.google.gson.Gson
import io.reactivex.Single

/**
 * SimilarityApi.kt - class that implement ISimilarityApi interface.
 * @see ISimilarityApi
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class SimilarityApi(private val similarityService: SimilarityService,
                             private var outputFormat: String,
                             private var language: String,
                             private var gson: Gson,
                             schedulerProvider: SdkSchedulerProvider,
                             apiHeader: ApiHeader,
                             endpoints: EndpointBuilder) : Api(schedulerProvider, apiHeader, endpoints), ISimilarityApi {
    /**
     * {@inheritDoc}
     */
    override fun outputFormat(outputFormat: String): ISimilarityApi {
        this.outputFormat = outputFormat
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun language(language: String): ISimilarityApi {
        this.language = language
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun getBySku(sku: String): Single<OfferResponseBody> {
        return getBySku(sku, OfferResponseBody::class.java)
    }

    /**
     * {@inheritDoc}
     */
    override fun <T : IResponse> getBySku(sku: String, clazz: Class<T>): Single<T> {
        val headers = createDefaultHeadersMap()
        headers["Accept"] = outputFormat
        headers["Accept-Language"] = language

        val obs1 = similarityService.getBySku(endpoints.getSimilarityUrl(sku), headers)

        return convertResponseBodyBasedOnType(sku, obs1, clazz, gson)
    }
}