package io.nyris.sdk

/**
 * Options.kt - Base class that contain common fields between all options
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Deprecated("Need to be removed with the next release 1.8.0")
abstract class Options {
    var enabled = false

    abstract fun reset()
}
