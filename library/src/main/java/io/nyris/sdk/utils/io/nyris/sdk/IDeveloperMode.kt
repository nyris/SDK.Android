package io.nyris.sdk

/**
 * IDebugMode.kt - interface that allow the possibility to enable developing mode on the SDK
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
interface IDeveloperMode{
    /**
     * Set Scheme
     *
     * @param scheme the scheme
     */
    fun setScheme(scheme : String)

    /**
     * Set Host Url
     *
     * @param hostUrl the host url
     */
    fun setHostUrl(hostUrl : String)

    /**
     * Set Api Version
     *
     * @param apiVersion the api version
     */
    fun setApiVersion(apiVersion : String)

    /**
     * Set Client Id
     *
     * @param clientId the api version
     */
    fun setClientId(clientId : String)
}