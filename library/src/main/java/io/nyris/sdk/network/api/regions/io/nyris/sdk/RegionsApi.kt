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

import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * RegionsApi.kt - class that implement IRegionsApi interface.
 * @see IRegionsApi
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class RegionsApi(
    private val regionsService: RegionsService,
    apiHeader: ApiHeader
) : Api(apiHeader), IRegionsApi {

    /**
     * {@inheritDoc}
     */
    override fun detect(image: ByteArray): Single<ObjectList> {
        val headers = createDefaultHeadersMap()
        headers["Content-Length"] = image.size.toString()
        val body = image.toRequestBody("image/jpg".toMediaTypeOrNull())

        return regionsService
            .detect(headers, body)
    }
}
