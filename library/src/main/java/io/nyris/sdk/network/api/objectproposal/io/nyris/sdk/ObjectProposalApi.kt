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
 * ObjectProposalApi.kt - class that implement IObjectProposalApi interface.
 * @see IObjectProposalApi
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class ObjectProposalApi(
    private val objectProposalService: ObjectProposalService,
    apiHeader: ApiHeader
) : Api(apiHeader), IObjectProposalApi {

    /**
     * {@inheritDoc}
     */
    override fun extractObjects(image: ByteArray): Single<List<ObjectProposal>> {
        val headers = createDefaultHeadersMap()
        headers["Content-Length"] = image.size.toString()
        val body = image.toRequestBody("image/jpg".toMediaTypeOrNull())

        return objectProposalService
            .extractObjects(headers, body)
    }
}
