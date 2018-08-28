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

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Nyris.kt - class implement INyris and IDeveloperMode
 * @see INyris
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class Nyris private constructor(apiKey: String, isDebug: Boolean) : INyris {
    private var apiHelper: IApiHelper = ApiHelper(apiKey, isDebug)

    /**
     * {@inheritDoc}
     */
    override fun setApiKey(apiKey: String): INyris {
        apiHelper.setApiKey(apiKey)
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun getApiKey(): String {
        return apiHelper.getApiKey()
    }

    /**
     * {@inheritDoc}
     */
    override fun imageMatching(): IImageMatchingApi {
        return apiHelper.imageMatching()
    }

    /**
     * {@inheritDoc}
     */
    override fun objectProposal(): IObjectProposalApi {
        return apiHelper.objectProposal()
    }

    /**
     * {@inheritDoc}
     */
    override fun notFoundMatching(): INotFoundMatchingApi {
        return apiHelper.notFoundMatching()
    }

    /**
     * {@inheritDoc}
     */
    override fun textSearch(): ITextSearchApi {
        return apiHelper.textSearch()
    }

    /**
     * {@inheritDoc}
     */
    override fun similarity(): ISimilarityApi {
        return apiHelper.similarity()
    }

    /**
     * {@inheritDoc}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun destroy() {
        instances.clear()
        compositeDisposable?.clear()
    }

    /**
     * companion Object
     */
    companion object {
        private var instances: HashMap<String, INyris> = hashMapOf()
        /**
         * Create instances of Nyris SDK
         *
         * @param apiKey the api key
         * @param isDebug the debug mode
         * @return the nyris sdk instance
         */
        @JvmStatic
        fun createInstance(apiKey: String, isDebug: Boolean = false): INyris {
            return if (instances.containsKey(apiKey))
                instances[apiKey] as INyris
            else {
                val instance = Nyris(apiKey, isDebug)
                instances[apiKey] = instance
                instance
            }
        }

        /**
         * Create instances of Nyris SDK
         *
         * @param apiKey the api key
         * @return the nyris sdk instance
         */
        @JvmStatic
        fun createInstance(apiKey: String): INyris {
            return createInstance(apiKey, false)
        }
    }
}

private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
fun Disposable.disposable() {
    compositeDisposable?.add(this)
}