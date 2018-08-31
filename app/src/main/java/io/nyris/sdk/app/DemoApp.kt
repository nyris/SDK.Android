package io.nyris.sdk.app

import android.app.Application
import io.nyris.sdk.INyris
import io.nyris.sdk.Nyris

/**
 *
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class DemoApp : Application(){
    lateinit var nyris : INyris

    override fun onCreate() {
        super.onCreate()
        nyris = Nyris.createInstance(BuildConfig.API_KEY, BuildConfig.DEBUG)
    }
}