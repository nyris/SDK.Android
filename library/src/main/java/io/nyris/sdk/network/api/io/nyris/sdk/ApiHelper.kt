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

import javax.inject.Inject

/**
 * ApiHelper.kt - class that implement IApiHelper and IDebugMode
 * @see IApiHelper
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class ApiHelper(apiKey: String, config: NyrisConfig) : IApiHelper {

    private lateinit var apiHeader: ApiHeader
    private lateinit var imageMatching: IImageMatchingApi
    private lateinit var regionsApi: IRegionsApi
    private lateinit var notFoundMatching: INotFoundMatchingApi
    private lateinit var textSearch: ITextSearchApi
    private lateinit var similarity: ISimilarityApi
    private lateinit var feedback: IFeedbackApi

    /**
     * Create instance of SdkComponent
     */
    private var sdkComponent: SdkComponent = DaggerSdkComponent
        .builder()
        .bindApiInfo(apiKey)
        .bindConfig(config)
        .build()

    init {
        sdkComponent.inject(this)
    }

    /**
     * Set Api Header
     * @param apiHeader the api header
     */
    @Inject
    fun setApiHeader(apiHeader: ApiHeader) {
        this.apiHeader = apiHeader
    }

    /**
     *
     * {@inheritDoc}
     */
    override fun setApiKey(apiKey: String): IApiHelper {
        apiHeader.apiKey = apiKey
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun getApiKey(): String {
        return apiHeader.apiKey
    }

    /**
     * Set Image Matching Api
     * @param imageMatching the image matching api
     */
    @Inject
    fun setImageMatching(imageMatching: IImageMatchingApi) {
        this.imageMatching = imageMatching
    }

    /**
     * {@inheritDoc}
     */
    override fun imageMatching(): IImageMatchingApi {
        return imageMatching
    }

    /**
     * Set region Api
     * @param regionsApi the region api
     */
    @Inject
    fun setRegionApi(regionsApi: IRegionsApi) {
        this.regionsApi = regionsApi
    }

    /**
     * {@inheritDoc}
     */
    override fun regions(): IRegionsApi {
        return regionsApi
    }

    /**
     * Set Not Found Api
     * @param notFoundMatching the not found matching api
     */
    @Inject
    fun setNotFoundMatching(notFoundMatching: INotFoundMatchingApi) {
        this.notFoundMatching = notFoundMatching
    }

    /**
     * {@inheritDoc}
     */
    override fun notFoundMatching(): INotFoundMatchingApi {
        return notFoundMatching
    }

    /**
     * Set Text Search Api
     * @param textSearch the text search api
     */
    @Inject
    fun setTextSearch(textSearch: ITextSearchApi) {
        this.textSearch = textSearch
    }

    /**
     * {@inheritDoc}
     */
    override fun textSearch(): ITextSearchApi {
        return textSearch
    }

    /**
     * Set Similarity Api
     * @param similarity the offer similarity api
     */
    @Inject
    fun setSimilarity(similarity: ISimilarityApi) {
        this.similarity = similarity
    }

    /**
     * {@inheritDoc}
     */
    override fun similarity(): ISimilarityApi {
        return similarity
    }

    /**
     * Set Similarity Api
     * @param feedback the feedback api
     */
    @Inject
    fun setFeedback(feedback: IFeedbackApi) {
        this.feedback = feedback
    }


    /**
     * {@inheritDoc}
     */
    override fun feedback(): IFeedbackApi {
        return feedback
    }
}
