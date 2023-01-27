package io.nyris.sdk

import androidx.annotation.Keep

/**
 * OcrOptions.kt - OCR Options that contains params related to it.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Deprecated("Need to be removed with the next release 1.8.0")
class OcrOptions : Options() {
    init {
        enabled = false
    }

    override fun reset() {
        enabled = true
    }
}
