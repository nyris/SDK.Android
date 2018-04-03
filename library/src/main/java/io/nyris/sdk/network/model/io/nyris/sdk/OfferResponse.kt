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

import okhttp3.Headers

/**
 * OfferResponse.kt - class model implement IResponse
 * @see IResponse
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class OfferResponse : IResponse {
    /**
     * Get Headers
     *
     * @return the headers
     */
    var headers : Headers? = null
        internal set
    /**
     * Get Offer Response Body
     *
     * @see OfferResponseBody
     * @return the offer response body
     */
    var body : OfferResponseBody? = null
        internal set

    /**
     * Get Request Id
     *
     * @return the request id
     */
    fun getRequestId() : String?{
        return headers!!["X-Matching-Request"]
    }
}