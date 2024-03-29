package io.nyris.sdk

/**
 * SimilarityOptions.kt - Similarity Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Deprecated("Need to be removed with the next release 1.8.0")
class SimilarityOptions : Options() {
    var threshold = -1F
    var limit = -1

    init {
        enabled = false
    }

    override fun reset() {
        enabled = false
        threshold = -1F
        limit = -1
    }
}
