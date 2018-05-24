package io.nyris.sdk

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

@Keep
class Region : Serializable {
    @SerializedName("left")
    var left: Float = 0.toFloat()

    @SerializedName("top")
    var top: Float = 0.toFloat()

    @SerializedName("right")
    var right: Float = 0.toFloat()

    @SerializedName("bottom")
    var bottom: Float = 0.toFloat()

    fun width(): Float {
        return right - left
    }

    fun height(): Float {
        return bottom - top
    }
}