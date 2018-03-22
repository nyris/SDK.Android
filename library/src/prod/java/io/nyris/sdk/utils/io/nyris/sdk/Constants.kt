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
 * Constants.kt - class constant to contain const value of the SDK
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal object Constants {
    const val SCHEME = "https"
    const val HOST_URL = "api.nyris.io"
    const val API_VERSION  = "v1"
    const val DEFAULT_OUTPUT_FORMAT = "application/offers.complete+json"
    const val DEFAULT_LANGUAGE = "*"
    const val NETWORK_CONNECTION_TIMEOUT = 30
    const val HTTP_RETRY_COUNT = 3
}