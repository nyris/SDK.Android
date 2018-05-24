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

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Offer.kt - class model
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Keep
open class Offer : Serializable{
    /**
     * Get Offer Id
     * @return String value
     */
    @SerializedName("oid")
    var id: String? = null
        protected set

    /**
     * Get Offer title
     * @return String value
     */
    @SerializedName("title")
    var title: String? = null
        protected set

    /**
     * Get Offer description
     * @return String value
     */
    @SerializedName("description")
    var description: String? = null
        protected set

    /**
     * Get Offer long description
     * @return String value
     */
    @SerializedName("descriptionLong")
    var descriptionLong: String? = null
        protected set

    /**
     * Get Offer language
     * @return String value
     */
    @SerializedName("language")
    var language: String? = null
        protected set

    /**
     * Get Offer brand
     * @return String value
     */
    @SerializedName("brand")
    var brand: String? = null
        protected set

    /**
     * Get Offer catalog numbers
     * @return Array of float
     */
    @SerializedName("catalogNumbers")
    var catalogNumbers: Array<String>? = null
        protected set

    /**
     * Get Offer custom ids
     * @return CustomIds object
     * @see CustomIds
     */
    @SerializedName("customIds")
    var customIds: CustomIds? = null
        protected set

    /**
     * Get Offer keywords
     * @return Array of String
     */
    @SerializedName("keywords")
    var keywords: Array<String>? = null
        protected set

    /**
     * Get Offer categories
     * @return Array of String
     */
    @SerializedName("categories")
    var categories: Array<String>? = null
        protected set

    /**
     * Get Offer availability
     * @return String value
     */
    @SerializedName("availability")
    var availability: String? = null
        protected set

    /**
     * Get Offer group ID
     * @return String value
     */
    @SerializedName("groupId")
    var groupId: String? = null
        protected set

    /**
     * Get Offer price
     * @return String value
     */
    @SerializedName("price")
    var priceStr: String? = null
        protected set

    /**
     * Get Offer sale price
     * @return String value
     */
    @SerializedName("salePrice")
    var salePrice: String? = null
        protected set

    /**
     * Get Offer Links
     * @return Links object
     * @see Links
     */
    @SerializedName("links")
    var links: Links? = null
        protected set

    /**
     * Get Offer images
     * @return Array of String
     */
    @SerializedName("images")
    var images: Array<String>? = null
        protected set

    /**
     * Get Offer metadata
     * @return String value
     */
    @SerializedName("metadata")
    var metadata: String? = null
        protected set

    /**
     * Get Offer SKU
     * @return String value
     */
    @SerializedName("sku")
    var sku: String? = null
        protected set

    /**
     * Get Offer SKU
     * @return float value
     */
    @SerializedName("score")
    var score: Float = 0.toFloat()
        protected set
}
