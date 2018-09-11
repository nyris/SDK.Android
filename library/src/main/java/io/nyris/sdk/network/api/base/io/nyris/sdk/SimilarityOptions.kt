package io.nyris.sdk

/**
 * SimilarityOptions.kt - Similarity Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class SimilarityOptions : Options() {
    var threshold = -1F
    var limit = -1

    init {
        enabled = true
    }

    override fun reset() {
        enabled = true
        threshold = -1F
        limit = -1
    }
}