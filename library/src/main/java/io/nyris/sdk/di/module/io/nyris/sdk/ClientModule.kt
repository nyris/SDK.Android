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

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * ClientModule.kt - class defines methods which provide singleton dependencies for Web Http Client creation.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Module
internal class ClientModule {

    /**
     * Provide HttpUrl
     * Used to provide HttpUrl for retrofit creation.
     *
     * @return the http url
     */
    @Provides
    @Singleton
    fun provideHttpUrl(config: NyrisConfig): HttpUrl =
        config.hostUrl.toHttpUrl()

    /**
     * Provide Api Header
     * Used as http headers.
     *
     * @param apiKey the api key
     * @param libraryId the library id
     * @param sdkVersion the sdk version
     * @param gitCommitHash the commit hash
     * @param androidVersion the android version
     * @return the api header
     */
    @Provides
    @Singleton
    fun provideApiHeader(
        @ApiKeyInfo apiKey: String,
        @SdkIdInfo libraryId: String,
        @SdkVersionInfo sdkVersion: String,
        @GitCommitHashInfo gitCommitHash: String,
        @AndroidVersionInfo androidVersion: String?
    ): ApiHeader = ApiHeader(apiKey, libraryId, sdkVersion, gitCommitHash, androidVersion)

    /**
     * Provide Http Logging Interceptor
     * Used to intercept content of the sent request and returned response.
     *
     * @return the http logging interceptor
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Provide Retry Interceptor
     * Used to intercept http number of retry.
     *
     * @param retryCount retry count
     * @return the retry interceptor
     */
    @Singleton
    @Provides
    fun provideRetryInterceptor(
        config: NyrisConfig
    ): Interceptor = RetryInterceptor(config.httpRetryCount)

    /**
     * Provide Gson
     * Used as json serializer or deserializer.
     *
     * @return the gson
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    /**
     * Provide Gson Converter Factory
     * Used as response converter
     *
     * @param gson the gson
     * @return the gson converter factory
     */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    /**
     * Provide RxJava CallAdapter Factory
     * Used as http call adapter.
     *
     * @return the rxjava call adapter Factory
     */
    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    /**
     * Provide Ok Http Client
     * Used as http client.
     *
     * @param isDebug the debug status
     * @param networkTimeoutInSeconds the network timeout
     * @param loggingInterceptor the http logging interceptor
     * @param retryInterceptor the http retry interceptor
     * @return the ok http client
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        config: NyrisConfig,
        loggingInterceptor: HttpLoggingInterceptor,
        retryInterceptor: Interceptor
    ): OkHttpClient = with(config) {
        OkHttpClient.Builder().apply {
            addInterceptor(retryInterceptor)
            connectTimeout(config.networkConnectionTimeOut, TimeUnit.SECONDS)
            if (isDebug) {
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }

    /**
     * Provide Retrofit
     * Used as Type-safe HTTP client.
     *
     * @param httpUrl the http url
     * @param converterFactory the gson converter factory
     * @param callAdapterFactory the rxjava call adapter factory
     * @param okHttpClient the ok http client
     * @return the retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        httpUrl: HttpUrl,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(httpUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(okHttpClient)
        .build()
}
