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
 * Links.kt - class model
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

@Keep
class Links : Serializable {
    /**
     * Get main link
     * @return String value
     */
    @SerializedName("main")
    var main: String? = null
        internal set

    /**
     * Get mobile link
     * @return String value
     */
    @SerializedName("mobile")
    var mobile: String? = null
        internal set

}