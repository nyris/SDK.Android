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

import android.os.Build
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * SdkModule.kt - class defines methods which provide singleton dependencies for user agent creation.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Module
internal class SdkModule {
    /**
     * Provide Library Id
     * Used to generate the user agent.
     *
     * @return the library id
     */
    @Provides
    @SdkIdInfo
    @Singleton
    fun provideLibraryId(): String {
        return BuildConfig.LIBRARY_PACKAGE_NAME
    }

    /**
     * Provide Sdk Version
     * Used to generate the user agent.
     *
     * @return the sdk version
     */
    @Provides
    @SdkVersionInfo
    @Singleton
    fun provideSdkVersion(): String {
        return BuildConfig.SDK_VERSION
    }

    /**
     * Provide Sdk Version
     * Used to generate the user agent.
     *
     * @return the sdk version
     */
    @Provides
    @GitCommitHashInfo
    @Singleton
    fun provideGitCommitHash(): String {
        return BuildConfig.GIT_COMMIT_HASH
    }

    /**
     * Provide Android Version
     * Used to generate the user agent.
     *
     * @return the android version
     */
    @Provides
    @AndroidVersionInfo
    @Singleton
    fun provideAndroidVersion(): String? {
        return Build.VERSION.RELEASE
    }
}
