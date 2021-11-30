package io.nyris.sdk

/**
 * RecommendationOptions.kt - Recommendation Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class RecommendationOptions : Options() {
    init {
        enabled = false
    }

    override fun reset() {
        enabled = false
    }
}
