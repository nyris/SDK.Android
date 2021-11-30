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

import android.annotation.SuppressLint
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Api.kt - class define common methods for child extended classes.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal open class Api(
    protected val schedulerProvider: SdkSchedulerProvider,
    protected val apiHeader: ApiHeader,
    protected val endpoints: EndpointBuilder
) {

    /**
     * Create Default Headers
     * This method helps to create default header for request {x}
     *
     * @return the headers hash map
     */
    fun createDefaultHeadersMap(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers["X-Api-Key"] = apiHeader.apiKey
        apiHeader.userAgent?.let {
            headers["User-Agent"] = it
        }
        // TODO : Check if client id is empty
        if (apiHeader.clientId.isNotEmpty()) {
            headers["X-Nyris-ClientID"] = apiHeader.clientId
        }
        return headers
    }

    /**
     * Ternary Xor
     * A method to check one of the parameters is enabled
     *
     * @return the boolean
     */
    fun ternaryOr(a: Boolean, b: Boolean, c: Boolean): Boolean {
        return a || b || c
    }

    /**
     * Build X-Options
     * This method builds x-options for specifics needs. can be overrode to build the desired x-options
     * for more details check {@link http://docs.nyris.io/}
     *
     * @return the x-options string
     */
    open fun buildXOptions(): String {
        return String()
    }

    /**
     * Convert Response Body Based On Type
     * This generic method convert ResponseBody to IResponse. this method will zip singles into one
     * single and return OfferResponseBody or JsonResponseBody object.
     *
     * @param id the id
     * @param obs1 the single response body
     * @param clazz the class
     * @param gson the gson
     *
     * @see IResponse
     * @see OfferResponse
     * @see JsonResponseBody
     *
     * @return the generic single
     */
    @SuppressLint("CheckResult")
    @Suppress("UNCHECKED_CAST")
    fun <T : IResponse, Z> convertResponseBodyBasedOnType(
        id: Z,
        obs1: Single<ResponseBody>,
        clazz: Class<T>,
        gson: Gson
    ): Single<T> {
        obs1
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

        val obs2 = Single.just(id)

        return Single
            .zip(obs1, obs2, { responseBody: ResponseBody, _: Z ->
                val strResponse = responseBody.string()
                val typeOfferResponseBody = OfferResponseBody::class.java
                if (typeOfferResponseBody.name == clazz.name) {
                    val offerResponse = gson.fromJson(strResponse, OfferResponseBody::class.java)
                    offerResponse as T
                } else {
                    val jsonResponse = JsonResponseBody()
                    jsonResponse.json = strResponse
                    jsonResponse as T
                }
            })
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    /**
     * Convert Response Based On Type
     * This generic method convert Response<OfferResponseBody> to IResponse. this method will zip
     * singles into one single and return offerResponse object.
     *
     * @param id the id
     * @param obs1 the single response offer body
     *
     * @see IResponse
     * @see OfferResponse
     * @see JsonResponseBody
     *
     * @return the generic single
     */
    @SuppressLint("CheckResult")
    @Suppress("UNCHECKED_CAST")
    fun <T, Z> convertResponseBasedOnType(
        id: Z?,
        obs1: Single<Response<OfferResponseBody>>
    ): Single<T> {
        obs1
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

        val obs2 = Single.just(id)

        return Single
            .zip(
                obs1,
                obs2,
                { response: Response<OfferResponseBody>, _: Z ->
                    //For the moment one class handling
                    val offerResponse = OfferResponse()
                    offerResponse.headers = response.headers()
                    offerResponse.body = response.body()
                    offerResponse as T
                }
            )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}
