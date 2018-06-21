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
 * EndpointBuilder.kt - class that build and provide api endpoints.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class EndpointBuilder (var scheme : String, var hostUrl: String, var apiVersion: String) {
    /**
     * Get Image Matching Api Url
     */
    var imageMatchingUrl: String = ""
        internal set
        get() {
            return BasicUriBuilder()
                    .scheme(scheme)
                    .authority(hostUrl)
                    .appendPath("find")
                    .appendPath(apiVersion)
                    .build()
        }

    /**
     * Get Image Matching Api Url
     */
    var imageMatchingUrl2: String = ""
        internal set
        get() {
            return BasicUriBuilder()
                    .scheme(scheme)
                    .authority(hostUrl)
                    .appendPath("find")
                    .appendPath(apiVersion)
                    .appendPath("fingerprint")
                    .appendPath("semantic")
                    .build()
        }

    /**
     * Get Object Proposal Api Url
     */
    var objectProposalUrl: String = ""
        get() {
            return BasicUriBuilder()
                    .scheme(scheme)
                    .authority(hostUrl)
                    .appendPath("find")
                    .appendPath(apiVersion)
                    .appendPath("regions")
                    .build()
        }

    /**
     * Get Text Search Api Url
     */
    var textSearchUrl: String = ""
        internal set
        get() {
            return BasicUriBuilder()
                    .scheme(scheme)
                    .authority(hostUrl)
                    .appendPath("find")
                    .appendPath(apiVersion)
                    .appendPath("text")
                    .build()
        }

    /**
     * Get Not Found Matching Api Url
     * Build Url of the not found offer endpoint
     */
    fun getNotFoundMatchingUrl(imageRequestId : String): String {
        return BasicUriBuilder()
                .scheme(scheme)
                .authority(hostUrl)
                .appendPath("find")
                .appendPath(apiVersion)
                .appendPath("manual")
                .appendPath(imageRequestId)
                .build()
    }

    /**
     * Get Offer Manager Api Url
     */
    fun getOfferManagerUrl(offerId : String) : String{
        return BasicUriBuilder()
                .scheme(scheme)
                .authority(hostUrl)
                .appendPath("manage")
                .appendPath(apiVersion)
                .appendPath("offers")
                .appendPath(offerId)
                .appendPath("images")
                .build()
    }

    /**
     * Get Similarity Api Url
     */
    fun getSimilarityUrl(sku : String) : String{
        return BasicUriBuilder()
                .scheme(scheme)
                .authority(hostUrl)
                .appendPath("recommend")
                .appendPath(apiVersion)
                .appendPath(sku)
                .build()
    }
}
