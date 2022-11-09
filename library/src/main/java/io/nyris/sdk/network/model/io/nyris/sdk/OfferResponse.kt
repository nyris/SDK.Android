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

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * OfferResponseBody.kt - class model implement IResponse
 * @see IResponse
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

@Keep
data class OfferResponse(
    @SerializedName("id")
    val requestId: String? = null,

    @SerializedName("session")
    val sessionId: String? = null,

    @SerializedName("predicted_category")
    val predictedCategories: Map<String, Float> = mapOf(),

    @SerializedName("results")
    val offers: List<Offer> = emptyList(),
) : IResponse

@Keep
data class Offer(
    @SerializedName("oid")
    val id: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("descriptionShort")
    val description: String? = null,

    @SerializedName("descriptionLong")
    val descriptionLong: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("brand")
    val brand: String? = null,

    @SerializedName("catalogNumbers")
    val catalogNumbers: List<String>? = null,

    @SerializedName("customIds")
    val customIds: Map<String, String>? = null,

    @SerializedName("keywords")
    val keywords: List<String>? = null,

    @SerializedName("categories")
    val categories: List<String>? = null,

    @SerializedName("availability")
    val availability: String? = null,

    @SerializedName("feedId")
    val feedId: String? = null,

    @SerializedName("groupId")
    val groupId: String? = null,

    @SerializedName("price")
    val priceStr: String? = null,

    @SerializedName("salePrice")
    val salePrice: String? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("images")
    val images: List<String>? = null,

    @SerializedName("metadata")
    val metadata: String? = null,

    @SerializedName("sku")
    val sku: String? = null,

    @SerializedName("score")
    val score: Float = 0.0F
)

@Keep
data class Links (
    @SerializedName("main")
    val main: String? = null,

    @SerializedName("mobile")
    val mobile: String? = null
)

