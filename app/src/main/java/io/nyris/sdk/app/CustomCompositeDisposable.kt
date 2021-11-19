package io.nyris.sdk.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.internal.functions.ObjectHelper
import io.reactivex.internal.util.ExceptionHelper
import io.reactivex.internal.util.OpenHashSet
import java.util.*

/**
 *
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
class CustomCompositeDisposable : LifecycleObserver, Disposable, DisposableContainer {
    private var resources: OpenHashSet<Disposable>? = null

    @Volatile
    private var disposed: Boolean = false

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dispose() {
        if (disposed) {
            return
        }
        var set: OpenHashSet<Disposable>? = null
        synchronized(this) {
            if (disposed) {
                return
            }
            disposed = true
            set = resources
            resources = null
        }

        dispose(set)
    }

    override fun isDisposed(): Boolean {
        return disposed
    }

    override fun add(d: Disposable): Boolean {
        ObjectHelper.requireNonNull(d, "d is null")
        if(disposed){
            d.dispose()
            return false
        }
        synchronized(this) {
            if (!disposed) {
                var set = resources
                if (set == null) {
                    set = OpenHashSet()
                    resources = set
                }
                set.add(d)
                return true
            }
        }
        return false
    }

    override fun remove(d: Disposable): Boolean {
        if (delete(d)) {
            d.dispose()
            return true
        }
        return false
    }

    override fun delete(d: Disposable): Boolean {
        ObjectHelper.requireNonNull(d, "Disposable item is null")
        if (disposed) {
            return false
        }
        synchronized(this) {
            if (disposed) {
                return false
            }

            val set = resources
            if (set == null || !set.remove(d)) {
                return false
            }
        }
        return true
    }

    /**
     * Atomically clears the container, then disposes all the previously contained Disposables.
     */
    fun clear() {
        if (disposed) {
            return
        }
        var set: OpenHashSet<Disposable>? = null
        synchronized(this) {
            if (disposed) {
                return
            }

            set = resources
            resources = null
        }

        dispose(set)
    }

    /**
     * Returns the number of currently held Disposables.
     * @return the number of currently held Disposables
     */
    fun size(): Int {
        if (disposed) {
            return 0
        }
        synchronized(this) {
            if (disposed) {
                return 0
            }
            val set = resources
            return set?.size() ?: 0
        }
    }

    /**
     * Dispose the contents of the OpenHashSet by suppressing non-fatal
     * Throwables till the end.
     * @param set the OpenHashSet to dispose elements of
     */
    private fun dispose(set: OpenHashSet<Disposable>?) {
        if (set == null) {
            return
        }
        var errors: MutableList<Throwable>? = null
        val array = set.keys()
        for (o in array) {
            if (o !is Disposable)
                continue
            try {
                o.dispose()
            } catch (ex: Throwable) {
                Exceptions.throwIfFatal(ex)
                if (errors == null) {
                    errors = ArrayList()
                }
                errors.add(ex)
            }
        }
        if (errors == null)
            return
        if (errors.size == 1) {
            throw ExceptionHelper.wrapOrThrow(errors[0])
        }
        throw CompositeException(errors)
    }
}