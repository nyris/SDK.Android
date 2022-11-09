package io.nyris.sdk.app

import android.app.Application
import io.nyris.sdk.INyris
import io.nyris.sdk.Nyris
import io.nyris.sdk.NyrisConfig

/**
 *
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class DemoApp : Application() {
    val nyris = Nyris.createInstance(
        apiKey = BuildConfig.API_KEY,
        config = NyrisConfig(isDebug = true)
    )
}
