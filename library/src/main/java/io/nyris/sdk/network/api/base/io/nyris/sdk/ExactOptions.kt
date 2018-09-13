package io.nyris.sdk

/**
 * ExactOptions.kt - Exact Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class ExactOptions : Options() {
    init {
        enabled = true
    }

    override fun reset() {
        enabled = true
    }
}