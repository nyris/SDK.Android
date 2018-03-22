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

import io.reactivex.Single

/**
 * ISimilarityApi.kt - interface for getting similar offer from similarity api.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
interface ISimilarityApi{
    /**
     * Set Output Format
     *
     * @param outputFormat the output format
     * @return the current instance of ISimilarityApi
     */
    fun outputFormat(outputFormat: String) : ISimilarityApi

    /**
     * Set Filter Language
     *
     * @param language the language
     * @return the current instance of IImageMatchingApi
     */
    fun language(language: String) : ISimilarityApi

    /**
     * Get Similar offer by sku
     *
     * Default get by sku method for the advanced response return, please use {@link #getBySku(sku : String, clazz : Class<T>) label}
     * @param sku the offer sku
     * @return the Single{Observable} OfferResponseBody
     */
    fun getBySku(sku : String) : Single<OfferResponseBody>

    /**
     * Generic Get Similar offer by sku
     *
     * @param sku the offer sku
     * @param clazz the desired return response class
     * @return the Single{Observable} IResponse {Could be {@link OfferResponseBody}, {@link OfferResponse} or {@link JsonResponseBody}
     */
    fun <T : IResponse> getBySku(sku : String, clazz : Class<T>) : Single<T>
}