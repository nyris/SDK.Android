package io.nyris.sdk

/**
 * RegroupOptions.kt - Regroup Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class RegroupOptions : Options() {
    var threshold = -1F

    init {
        enabled = false
    }

    override fun reset() {
        enabled = false
        threshold = -1F
    }
}