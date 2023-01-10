package io.nyris.sdk

import androidx.annotation.Keep

@Keep
class FiltersOptions {
    var list: List<Filter> = emptyList()

    fun reset() {
        list = emptyList()
    }
}

@Keep
class Filter(val filterType: String, val filterValue: List<String>)