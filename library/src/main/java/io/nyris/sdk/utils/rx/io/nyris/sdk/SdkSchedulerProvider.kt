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

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * SdkSchedulerProvider.kt - class that implement SchedulerProvider
 * @see SchedulerProvider
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
internal class SdkSchedulerProvider : SchedulerProvider {
    /**
     * {@inheritDoc}
     */
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    /**
     * {@inheritDoc}
     */
    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    /**
     * {@inheritDoc}
     */
    override fun io(): Scheduler {
        return Schedulers.io()
    }
}