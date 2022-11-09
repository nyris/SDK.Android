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
 * ObjectList.kt - class model
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

@Keep
data class ObjectList(
    @SerializedName("regions")
    val regions: List<Object>? = null
)

@Keep
data class Object(
    @SerializedName("confidence")
    val confidence: Float = 0.0F,

    @SerializedName("region")
    val region: Region? = null
)

@Keep
data class Region(
    @SerializedName("left")
    val left: Int = 0,

    @SerializedName("top")
    val top: Int = 0,

    @SerializedName("right")
    val right: Int = 0,

    @SerializedName("bottom")
    val bottom: Int = 0,
) {
    fun width(): Int = right - left

    fun height(): Int = bottom - top
}
