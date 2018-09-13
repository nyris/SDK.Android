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
 * IImageMatchingApi.kt - interface for sending request to image matching api.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
interface IImageMatchingApi {
    /**
     * Set Output Format
     *
     * @param outputFormat the output format
     * @return the current instance of IImageMatchingApi
     */
    fun outputFormat(outputFormat: String): IImageMatchingApi

    /**
     * Set Filter Language
     *
     * @param language the language
     * @return the current instance of IImageMatchingApi
     */
    fun language(language: String): IImageMatchingApi

    /**
     * Enable exact matching phase
     *
     * @param isEnabled boolean value, true to enable exact matching phase and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("similarity(Boolean) is deprecated and will be remove in version 1.6.")
    fun exact(isEnabled: Boolean): IImageMatchingApi

    /**
     * Enable exact matching phase
     *
     * @param action parameter function function type with ExactOptions receiver.
     * @return the current instance of IImageMatchingApi
     * @see ExactOptions
     */
    fun exact(action: ExactOptions.() -> Unit = { enabled = true }): IImageMatchingApi

    /**
     * Enable similarity phase
     *
     * @param isEnabled boolean value, true to enable similarity matching phase and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("similarity(Boolean) is deprecated and will be remove in version 1.6.")
    fun similarity(isEnabled: Boolean): IImageMatchingApi

    /**
     * Enable similarity phase
     *
     * @param action parameter function function type with SimilarityOptions receiver.
     * @return the current instance of IImageMatchingApi
     * @see SimilarityOptions
     */
    fun similarity(action: SimilarityOptions.() -> Unit = { enabled = true }): IImageMatchingApi

    /**
     * Set Similarity Return Limit
     *
     * @param limit limit int value between 1-100 helps to limit returned similarity response.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("similarityLimit(Int) is deprecated and will be remove in version 1.6.")
    fun similarityLimit(@IntRange(from = 1, to = 100) limit: Int): IImageMatchingApi

    /**
     * Set Similarity Threshold
     *
     * @param threshold threshold int value between 0-1 helps to return only offer with score above the threshold
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("similarityThreshold(Float) is deprecated and will be remove in version 1.6.")
    fun similarityThreshold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): IImageMatchingApi

    /**
     * Enable OCR phase
     *
     * @param isEnabled boolean value, true to enable ocr matching phase and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("ocr(Boolean) is deprecated and will be remove in version 1.6.")
    fun ocr(isEnabled: Boolean): IImageMatchingApi

    /**
     * Enable OCR phase
     *
     * @param action parameter function function type with OcrOptions receiver.
     * @return the current instance of IImageMatchingApi
     * @see OcrOptions
     */
    fun ocr(action: OcrOptions.() -> Unit = { enabled = true }): IImageMatchingApi

    /**
     * Set Return Limit
     *
     * @param limit limit int value between 1-100 helps to limit returned response.
     * @return the current instance of IImageMatchingApi
     */
    fun limit(@IntRange(from = 1, to = 100) limit: Int): IImageMatchingApi

    /**
     * Enable Offers Regroup
     *
     * @param isEnabled boolean value, true to enable offer regrouping and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("regroup(Boolean) is deprecated and will be remove in version 1.6.")
    fun regroup(isEnabled: Boolean): IImageMatchingApi

    /**
     * Enable Offers Regroup
     *
     * @param action parameter function function type with RegroupOptions receiver.
     * @return the current instance of IImageMatchingApi
     * @see RegroupOptions
     */
    fun regroup(action: RegroupOptions.() -> Unit = { enabled = true }): IImageMatchingApi

    /**
     * Set Regroup Threshold
     *
     * @param threshold threshold int value between 0-1 helps to return only offer with score above the threshold
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("regroupThreshold(Float) is deprecated and will be remove in version 1.6.")
    fun regroupThreshold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): IImageMatchingApi

    /**
     * Enable Recommendation XOptions
     *
     * @param isEnabled boolean value, true to enable offer recommendations and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("recommendations(Boolean) is deprecated and will be remove in version 1.6.")
    fun recommendations(isEnabled: Boolean): IImageMatchingApi

    /**
     * Enable Offer Recommendation
     *
     * @param action parameter function function type with RecommendationOptions receiver.
     * @return the current instance of IImageMatchingApi
     * @see RecommendationOptions
     */
    fun recommendations(action: RecommendationOptions.() -> Unit = { enabled = true }): IImageMatchingApi

    /**
     * Enable the output of predicted categories.
     *
     * @param isEnabled boolean value, true to enable categories predictions and false for disabled it.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("categoryPrediction(Boolean) is deprecated and will be remove in version 1.6.")
    fun categoryPrediction(isEnabled: Boolean): IImageMatchingApi

    /**
     * Enable the output of predicted categories.
     *
     * @param action parameter function function type with CategoryPredictionOptions receiver.
     * @return the current instance of IImageMatchingApi
     * @see CategoryPredictionOptions
     */
    fun categoryPrediction(action: CategoryPredictionOptions.() -> Unit = { enabled = true }): IImageMatchingApi

    /**
     * Set Similarity Return Limit
     *
     * @param limit limit int value between 1-100 helps to limit returned category response.
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("categoryPredictionLimit(Int) is deprecated and will be remove in version 1.6.")
    fun categoryPredictionLimit(@IntRange(from = 1, to = 100) limit: Int): IImageMatchingApi

    /**
     * Set Similarity Threshold
     *
     * @param threshold threshold int value between 0-1 helps to return only category with score above the threshold
     * @return the current instance of IImageMatchingApi
     */
    @Deprecated("categoryPredictionThreshold(Float) is deprecated and will be remove in version 1.6.")
    fun categoryPredictionThreshold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): IImageMatchingApi

    /**
     * Match image byte array
     *
     * Default image matching method for the advanced response return, please use {@link #match(image : ByteArray, clazz : Class<T>) label}
     * @param image the image byte array
     * @return the Single{Observable} OfferResponseBody
     */
    fun match(image: ByteArray): Single<OfferResponseBody>

    /**
     * Match image float array
     *
     * Default image matching method for the advanced response return, please use {@link #match(image : ByteArray, clazz : Class<T>) label}
     * @param image the image float array
     * @return the Single{Observable} OfferResponseBody
     */
    fun match(image: FloatArray): Single<OfferResponseBody>

    /**
     * Generic Match image byte array
     *
     * @param image the image byte array
     * @param clazz the desired return response class
     * @return the Single{Observable} IResponse {Could be {@link OfferResponseBody}, {@link OfferResponse} or {@link JsonResponseBody}
     */
    fun <T : IResponse> match(image: ByteArray, clazz: Class<T>): Single<T>

    /**
     * Generic Match image float array
     *
     * @param image the image float array
     * @param clazz the desired return response class
     * @return the Single{Observable} IResponse {Could be {@link OfferResponseBody}, {@link OfferResponse} or {@link JsonResponseBody}
     */
    fun <T : IResponse> match(image: FloatArray, clazz: Class<T>): Single<T>
}