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
import io.reactivex.Single

/**
 * ITextSearchApi.kt - interface for searching offer based on text using text search api.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
interface ITextSearchApi {
    /**
     * Set Output Format
     *
     * @param outputFormat the output format
     * @return the current instance of IImageMatchingApi
     */
    fun outputFormat(outputFormat: String) : ITextSearchApi

    /**
     * Set Filter Language
     *
     * @param language the language
     * @return the current instance of IImageMatchingApi
     */
    fun language(language: String) : ITextSearchApi

    /**
     * Set Return Limit
     *
     * @param limit limit int value between 1-100 helps to limit returned response.
     * @return the current instance of IImageMatchingApi
     */
    fun limit(@IntRange(from=1, to=100) limit : Int) : ITextSearchApi

    /**
     * Enable Offers Regroup
     *
     * @param isEnabled boolean value, true to enable offer regrouping and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    fun regroup(isEnabled : Boolean) : ITextSearchApi

    /**
     * Set Regroup Threshold
     *
     * @param threshold threshold int value between 0-1 helps to return only offer with score above the threshold
     * @return the current instance of IImageMatchingApi
     */
    fun regroupThreshold(@FloatRange(from=0.0, to=1.0) threshold: Float) : ITextSearchApi

    /**
     * Search Offers
     *
     * Default search offers method for the advanced response return, please use {@link #searchOffers(keyword : String, clazz : Class<T>) label}
     * @param keyword the keyword
     * @return the Single{Observable} OfferResponseBody
     */
    fun searchOffers(keyword: String) : Single<OfferResponseBody>

    /**
     * Generic Search Offers
     *
     * @param keyword the keyword
     * @param clazz the desired return response class
     * @return the Single{Observable} IResponse {Could be {@link OfferResponseBody}, {@link OfferResponse} or {@link JsonResponseBody}
     */
    fun <T : IResponse> searchOffers(keyword : String, clazz : Class<T>) : Single<T>
}