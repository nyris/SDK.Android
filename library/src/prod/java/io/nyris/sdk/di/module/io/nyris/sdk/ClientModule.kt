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

import android.net.Uri
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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
internal class ClientModule(private val apiKey: String, private val isDebug: Boolean){
    /**
     * Provide debug status
     * Used to enable debug output of the sdk.
     *
     * @return the debug status
     */
    @Provides
    @DebugInfo
    fun provideIsDebug(): Boolean {
        return isDebug
    }

    /**
     * Provide Network Time Out
     * Used to set web client timeout.
     *
     * @return the time out value in seconds
     */
    @Provides
    @NetworkTimeOutInfo
    fun provideNetworkTimeoutInSeconds(): Int {
        return Constants.NETWORK_CONNECTION_TIMEOUT
    }

    /**
     * Provide Api Key
     * Used to authorise sent http requests from the SDK.
     *
     * @return the api key
     */
    @Provides
    @ApiInfo
    fun provideApiKey(): String {
        return apiKey
    }

    /**
     * Provide HTTP Scheme
     * Used to provide http scheme for url building.
     *
     * @return the http scheme
     */
    @Provides
    @SchemeInfo
    fun provideScheme(): String {
        return Constants.SCHEME
    }

    /**
     * Provide HOST URL
     * Used to provide host url for url building
     *
     * @return the http scheme
     */
    @Provides
    @HostUrlInfo
    fun provideHostUrl(): String {
        return Constants.HOST_URL
    }

    /**
     * Provide Api Version
     * Used to provide api version for url building.
     *
     * @return the api version
     */
    @Provides
    @ApiVersionInfo
    fun provideApiVersion(): String {
        return Constants.API_VERSION
    }

    /**
     * Provide HttpUrl
     * Used to provide HttpUrl for retrofit creation.
     *
     * @return the http url
     */
    @Provides
    @Singleton
    fun provideHttpUrl(): HttpUrl {
        val builder = BasicUriBuilder()
                .scheme(Constants.SCHEME)
                .authority(Constants.HOST_URL)
        return HttpUrl.parse(builder.build())!!
    }

    /**
     * Provide Default OutputFormat
     * Used as request header to get different json response based on the specified output format.
     *
     * @return the default output format
     */
    @Provides
    @OutputFormatInfo
    fun provideDefaultOutputFormat(): String {
        return Constants.DEFAULT_OUTPUT_FORMAT
    }

    /**
     * Provide Default Language
     * Used as request header to get only offers base on specified language.
     *
     * @return the default language
     */
    @Provides
    @LanguageInfo
    fun provideDefaultLanguage(): String {
        return Constants.DEFAULT_LANGUAGE
    }

    /**
     * Provide Nyris Endpoints Builder
     * Used to generate endpoints.
     *
     * @param scheme the scheme
     * @param hostUrl the host url
     * @param apiVersion the api version
     * @return the nyris endpoint
     */
    @Provides
    @Singleton
    fun provideNyrisEndpoints(@SchemeInfo scheme: String,
                              @HostUrlInfo hostUrl : String,
                              @ApiVersionInfo apiVersion : String): EndpointBuilder {
        return EndpointBuilder(scheme, hostUrl, apiVersion)
    }

    /**
     * Provide Api Retry Count
     * Used to retry failed http request until client reach retry count or successful request.
     *
     * @return the retry count
     */
    @Provides
    @RetryCountInfo
    fun provideApiRetryCount(): Int {
        return Constants.HTTP_RETRY_COUNT
    }

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
    fun provideApiHeader(@ApiInfo apiKey: String,
                         @SdkIdInfo libraryId : String,
                         @SdkVersionInfo sdkVersion : String,
                         @GitCommitHashInfo gitCommitHash : String,
                         @AndroidVersionInfo androidVersion : String?): ApiHeader {
        return ApiHeader(apiKey, libraryId, sdkVersion, gitCommitHash, androidVersion)
    }

    /**
     * Provide Http Logging Interceptor
     * Used to intercept content of the sent request and returned response.
     *
     * @return the http logging interceptor
     */
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
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
    fun provideRetryInterceptor(@RetryCountInfo retryCount: Int): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            var response: Response? = null
            var exception: IOException? = null

            var tryCount = 0
            // retry the request
            while (tryCount < retryCount && (null == response || !response.isSuccessful)) try {
                response = chain.proceed(request)
            } catch (e: IOException) {
                exception = e
            } finally {
                tryCount++
            }

            // throw last exception
            if (null == response && null != exception)
                throw exception

            // otherwise just pass the original response on
            response
        }
    }

    /**
     * Provide Gson
     * Used as json serializer or deserializer.
     *
     * @return the gson
     */
    @Singleton
    @Provides
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
    @Singleton
    @Provides
    fun provideOkHttpClient(
            @DebugInfo isDebug: Boolean,
            @NetworkTimeOutInfo networkTimeoutInSeconds: Int,
            loggingInterceptor : HttpLoggingInterceptor,
            retryInterceptor: Interceptor): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(retryInterceptor)
                .connectTimeout(networkTimeoutInSeconds.toLong(), TimeUnit.SECONDS)

        //show logs if sdk is in Debug mode
        if (isDebug){
            okHttpClient.addInterceptor(loggingInterceptor)
        }

        return okHttpClient.build()
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
    fun provideRetrofit(httpUrl : HttpUrl,
                        converterFactory: Converter.Factory,
                        callAdapterFactory: CallAdapter.Factory,
                        okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build()
    }
}