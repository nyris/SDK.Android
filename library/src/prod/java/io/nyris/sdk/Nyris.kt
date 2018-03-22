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

/**
 * Nyris.kt - class implement INyris and IDeveloperMode
 * @see INyris
 * @see IDeveloperMode
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class Nyris private constructor(apiKey: String, isDebug: Boolean) : INyris, IDeveloperMode {
    private var apiHelper: IApiHelper = ApiHelper(apiKey, isDebug)

    /**
     * {@inheritDoc}
     */
    override fun setApiKey(apiKey: String) : INyris {
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
    override fun setScheme(scheme: String) {
        val debugMode = apiHelper as IDeveloperMode
        debugMode.setScheme(scheme)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHostUrl(hostUrl: String) {
        val debugMode = apiHelper as IDeveloperMode
        debugMode.setScheme(hostUrl)
    }

    /**
     * {@inheritDoc}
     */
    override fun setApiVersion(apiVersion: String) {
        val debugMode = apiHelper as IDeveloperMode
        debugMode.setScheme(apiVersion)
    }

    /**
     * {@inheritDoc}
     */
    override fun setClientId(clientId: String) {
        val debugMode = apiHelper as IDeveloperMode
        debugMode.setClientId(clientId)
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
    override fun manualMatching(): IManualMatchingApi {
        return apiHelper.manualMatching()
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
    override fun destroy(){
        instance = null
    }

    /**
     * companion Object
     */
    companion object {
        private var instance: INyris? = null
        /**
         * Get instance of Nyris SDK
         *
         * @param apiKey the api key
         * @param isDebug the debug mode
         * @return the nyris sdk
         */
        fun getInstance(apiKey: String, isDebug: Boolean = false) : INyris {
            if(instance == null)
                instance = Nyris(apiKey, isDebug)
            else
                instance!!.setApiKey(apiKey)
            return instance as INyris
        }

        /**
         * Get instance of Nyris SDK
         *
         * @param apiKey the api key
         * @return the nyris sdk
         */
        fun getInstance(apiKey: String) : INyris {
            if(instance == null)
                instance = Nyris(apiKey, false)
            else
                instance!!.setApiKey(apiKey)
            return instance as INyris
        }
    }
}