package io.nyris.sdk

/**
 * BasicUriBuilder.kt - class Basic Uri Builder
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class BasicUriBuilder{
    var scheme : String? = null
    var authority : String? = null
    var listPath : MutableList<String> = mutableListOf()

    /**
     * set Scheme
     *
     * @param scheme the scheme
     * @return Current instance of BasicUriBuilder
     */
    fun scheme(scheme : String) : BasicUriBuilder{
        this.scheme = scheme
        return this
    }

    /**
     * set Authority
     *
     * @param authority the authority
     * @return Current instance of BasicUriBuilder
     */
    fun authority(authority : String) : BasicUriBuilder{
        this.authority = authority
        return this
    }

    /**
     * Append Path
     *
     * @param path the path
     * @return Current instance of BasicUriBuilder
     */
    fun appendPath(path : String) : BasicUriBuilder{
        listPath.add(path)
        return this
    }

    /**
     * Build Uri
     *
     * @return the uri
     */
    fun build() : String{
        var url = "$scheme://$authority/"
        listPath.forEach({
            url+= "$it/"
        })

        return url
    }
}