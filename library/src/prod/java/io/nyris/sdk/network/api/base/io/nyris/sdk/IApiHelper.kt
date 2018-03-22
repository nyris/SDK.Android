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

/**
 * IApiHelper.kt - interface facade for the api classes.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
interface IApiHelper {
    /**
     * Set Api Key
     *
     * @param apiKey the api key
     * @return the any object
     */
    fun setApiKey(apiKey : String) : Any

    /**
     * Get Current Api Key
     *
     * @return the api key
     */
    fun getApiKey() : String

    /**
     * Get Image Matching Api instance
     *
     * @see IImageMatchingApi
     * @return the image matching api
     */
    fun imageMatching() : IImageMatchingApi

    /**
     * Get Object Proposal Api instance
     *
     * @see IObjectProposalApi
     * @return the image matching api
     */
    fun objectProposal() : IObjectProposalApi

    /**
     * Get Manual Matching Api
     *
     * @see IManualMatchingApi
     * @return the manual matching api
     */
    fun manualMatching() : IManualMatchingApi

    /**
     * Get Text Search Api
     *
     * @see ITextSearchApi
     * @return the text search api
     */
    fun textSearch() : ITextSearchApi

    /**
     * Get Similarity Api
     *
     * @see ISimilarityApi
     * @return the similarity api
     */
    fun similarity() : ISimilarityApi
}