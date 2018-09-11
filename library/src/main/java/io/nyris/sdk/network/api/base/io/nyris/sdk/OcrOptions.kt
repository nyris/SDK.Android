package io.nyris.sdk

/**
 * OcrOptions.kt - OCR Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class OcrOptions : Options() {
    init {
        enabled = true
    }

    override fun reset() {
        enabled = true
    }
}