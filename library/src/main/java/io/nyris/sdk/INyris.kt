/*
 * Copyright (C) 2018 nyris GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nyris.sdk

import androidx.lifecycle.LifecycleObserver

/**
 * INyris.kt - interface that inherit all the features of IApiHelper and implement LifecycleObserver
 * @see IApiHelper
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
interface INyris : IApiHelper, LifecycleObserver {
    /**
     * {@inheritDoc}
     */
    override fun setApiKey(apiKey: String): INyris

    /**
     * Destroy all the instances of INyris
     */
    fun destroy()
}
