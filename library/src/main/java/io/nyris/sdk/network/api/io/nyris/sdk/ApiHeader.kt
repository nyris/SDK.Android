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
 * ApiHeader.kt - class model for Api Header, act Api key container and user agent builder.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

internal class ApiHeader constructor(
    var apiKey: String,
    private val sdkId: String,
    private val sdkVersion: String,
    private val gitCommitHash: String,
    private val androidVersion: String?
) {
    /**
     * Get User Agent
     */
    var userAgent: String? = null
        get() {
            if (field == null) {
                field = "$sdkId/$sdkVersion ($gitCommitHash Android $androidVersion)"
            }
            return field
        }

    var clientId: String = ""
}
