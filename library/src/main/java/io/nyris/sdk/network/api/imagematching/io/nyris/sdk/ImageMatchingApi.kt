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
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * ImageMatchingApi.kt - class that implement IImageMatchingApi interface.
 * @see IImageMatchingApi
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class ImageMatchingApi(private val imageMatchingService : ImageMatchingService,
                       private var outputFormat : String,
                       private var language : String,
                       private var gson : Gson,
                       schedulerProvider : SdkSchedulerProvider,
                       apiHeader: ApiHeader,
                       endpoints: EndpointBuilder) : Api(schedulerProvider,apiHeader,endpoints), IImageMatchingApi {
    private var enableExact : Boolean = true
    private var enableSimilarity : Boolean = true
    private var similarityLimit : Int = -1
    private var similarityThreshold : Float = -1F
    private var enableOcr : Boolean = true
    private var enableRegroup : Boolean = false
    private var regroupThreshold : Float = -1F
    private var limit : Int = 20

    /**
     * Init local properties
     */
    private fun `init`(){
        enableExact = true
        enableSimilarity = true
        similarityLimit = -1
        similarityThreshold = -1F
        enableOcr = true
        enableRegroup = false
        regroupThreshold = -1F
        limit = 20
    }
    /**
     * {@inheritDoc}
     */
    override fun outputFormat(outputFormat: String): ImageMatchingApi {
        this.outputFormat = outputFormat
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun language(language: String): ImageMatchingApi {
        this.language = language
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun exact(isEnabled: Boolean): IImageMatchingApi {
        enableExact = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun similarity(isEnabled: Boolean): IImageMatchingApi {
        enableSimilarity = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun similarityLimit(@IntRange(from=1, to=100) limit: Int): IImageMatchingApi {
        similarityLimit = limit
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun similarityThreshold(@FloatRange(from=0.0, to=1.0) threshold: Float): IImageMatchingApi {
        similarityThreshold = threshold
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun ocr(isEnabled: Boolean): IImageMatchingApi {
        enableOcr = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun limit(limit: Int): IImageMatchingApi {
        this.limit = limit
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroup(isEnabled: Boolean): IImageMatchingApi {
        enableRegroup = isEnabled
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun regroupThreshold(@FloatRange(from=0.0, to=1.0) threshold: Float): IImageMatchingApi {
        regroupThreshold = threshold
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun buildXOptions(): String {
        var xOptions = ""

        if(enableExact && xOptions.isEmpty())
            xOptions = "exact"

        if(enableSimilarity && xOptions.isEmpty())
            xOptions = "similarity"
        else
            if(enableSimilarity)
                xOptions+= " +similarity"

        if(enableOcr && xOptions.isEmpty())
            xOptions = "ocr"
        else
            if(enableOcr)
                xOptions+= " +ocr"

        if(enableSimilarity && similarityLimit!= -1)
            xOptions+= " similarity.limit=$similarityLimit"

        if(enableSimilarity && similarityThreshold!= -1F)
            xOptions+= " similarity.threshold=$similarityThreshold"

        if(enableRegroup)
            xOptions +=" +regroup"

        if(enableRegroup && regroupThreshold!= -1F)
            xOptions += " regroup.threshold=$regroupThreshold"

        if(limit!= 20)
            xOptions+= " limit=$limit"

        init()
        return xOptions
    }

    /**
     * Build Headers for image matching endpoint
     */
    private fun buildHeaders(image: ByteArray) :  HashMap<String, String>{
        val headers = createDefaultHeadersMap()
        headers["Accept"] = outputFormat
        headers["Accept-Language"] = language
        headers["Content-Length"] = image.size.toString()
        headers["X-Options"] = buildXOptions()
        return headers
    }

    /**
     * {@inheritDoc}
     */
    override fun match(image: ByteArray): Single<OfferResponseBody> {
        return match(image, OfferResponseBody::class.java)
    }

    /**
     * {@inheritDoc}
     */
    override fun <T : IResponse> match(image: ByteArray, clazz: Class<T>): Single<T> {
        val headers = buildHeaders(image)
        val body = RequestBody.create(MediaType.parse("image/jpg"), image)
        val typeOfferResponse = OfferResponse::class.java

        return if(clazz.name == typeOfferResponse.name){
            val obs1 = imageMatchingService.matchAndGetRequestId(endpoints.imageMatchingUrl, headers, body)
            convertResponseBasedOnType(image, obs1)
        }else{
            val obs1 = imageMatchingService.match(endpoints.imageMatchingUrl, headers, body)
            convertResponseBodyBasedOnType(image, obs1, clazz, gson)
        }
    }
}