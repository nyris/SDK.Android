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
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * ServiceModule.kt - class defines methods which provide singleton dependencies for service creation.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@Module
internal class ServiceModule {

    /**
     * Provide Image Matching Service
     * Used as service to send image byte array to the defined endpoint by the the {@code service}.
     *
     * @param retrofit the retrofit
     * @return the image matching service
     */
    @Provides
    @Singleton
    fun provideImageMatchingService(retrofit: Retrofit): ImageMatchingService =
        retrofit.create(ImageMatchingService::class.java)

    /**
     * Provide Image Object Proposal Service
     * Used as service to send image byte array to the defined endpoint by the the {@code service}.
     *
     * @param retrofit the retrofit
     * @return the image object proposal service
     */
    @Provides
    @Singleton
    fun provideObjectProposalService(retrofit: Retrofit): ObjectProposalService =
        retrofit.create(ObjectProposalService::class.java)

    /**
     * Provide Not Found Matching Service
     * Used as service to mark unrecognized image as not found to the defined endpoint by the the {@code service}.
     *
     * @param retrofit the retrofit
     * @return the not fount matching service
     */
    @Provides
    @Singleton
    fun provideNotFoundMatchingService(retrofit: Retrofit): NotFoundMatchingService =
        retrofit.create(NotFoundMatchingService::class.java)

    /**
     * Provide Text Search Offer Service
     * Used as service to send text to the defined endpoint by the the {@code service}.
     *
     * @param retrofit the retrofit
     * @return the search offer service
     */
    @Provides
    @Singleton
    fun provideTextSearchOfferService(retrofit: Retrofit): TextSearchService =
        retrofit.create(TextSearchService::class.java)

    /**
     * Provide Similarity Service
     * Used as service to send sku to the defined endpoint by the the {@code service}.
     *
     * @param retrofit the retrofit
     * @return the similarity service
     */
    @Provides
    @Singleton
    fun provideSimilarityService(retrofit: Retrofit): SimilarityService =
        retrofit.create(SimilarityService::class.java)

    /**
     * Provide Feedback Service
     * Used as service to log user feedback {@code service}.
     *
     * @param retrofit the retrofit
     * @return the feedback service
     */
    @Provides
    @Singleton
    fun provideFeedbackService(retrofit: Retrofit): FeedbackService =
        retrofit.create(FeedbackService::class.java)

    /**
     * Provide Image Matching Api
     * Used as api to match image byte array.
     *
     * @param imageMatchingService the image matching service
     * @param outputFormat the output format
     * @param language the language
     * @param gson the gson
     * @param schedulerProvider the skd scheduler
     * @param apiHeader the api header
     * @param endpoints the endpoint builder
     * @return the image matching api
     */
    @Provides
    @Singleton
    fun provideImageMatchingApi(
        config: NyrisConfig,
        imageMatchingService: ImageMatchingService,
        gson: Gson,
        apiHeader: ApiHeader,
    ): IImageMatchingApi = ImageMatchingApi(
        imageMatchingService,
        config.defaultOutputFormat,
        config.defaultLanguage,
        gson,
        apiHeader
    )

    /**
     * Provide Object Proposal Api
     * Used as api to extract objects from image byte array.
     *
     * @param objectProposalService the object proposal service
     * @param schedulerProvider the sdk scheduler
     * @param apiHeader the api header
     * @param endpoints the endpoint builder
     * @return the object proposal api
     */
    @Provides
    @Singleton
    fun provideObjectProposalApi(
        objectProposalService: ObjectProposalService,
        apiHeader: ApiHeader,
    ): IObjectProposalApi = ObjectProposalApi(objectProposalService, apiHeader)

    /**
     * Provide Not Found Matching Api
     * Used as api to to set image {X} to be matched manually.
     *
     * @param notFoundMatchingService the not found matching service
     * @param schedulerProvider the sdk scheduler
     * @param apiHeader the api header
     * @param endpoints the endpoint builder
     * @return the not found matching api
     */
    @Provides
    @Singleton
    fun provideNotFoundMatchingApi(
        notFoundMatchingService: NotFoundMatchingService,
        apiHeader: ApiHeader
    ): INotFoundMatchingApi = NotFoundMatchingApi(notFoundMatchingService, apiHeader)

    /**
     * Provide Text Search Api
     * Used as api to search offer based on the provided text.
     *
     * @param textSearchService the text search service
     * @param schedulerProvider the sdk scheduler
     * @param apiHeader the api header
     * @param endpoints the endpoint builder
     * @return the text search api
     */
    @Provides
    @Singleton
    fun provideTextSearchApi(
        config: NyrisConfig,
        textSearchService: TextSearchService,
        gson: Gson,
        apiHeader: ApiHeader
    ): ITextSearchApi = TextSearchApi(
        textSearchService,
        config.defaultOutputFormat,
        config.defaultLanguage,
        gson,
        apiHeader
    )

    /**
     * Provide Similarity Api
     * Used as api to get similar offers based on sku.
     *
     * @param similarityService the similarity service
     * @param schedulerProvider the sdk scheduler
     * @param apiHeader the api header
     * @param endpoints the endpoint builder
     * @return the similarity api
     */
    @Provides
    @Singleton
    fun provideSimilarityApi(
        config: NyrisConfig,
        similarityService: SimilarityService,
        gson: Gson,
        apiHeader: ApiHeader
    ): ISimilarityApi = SimilarityApi(
        similarityService,
        config.defaultOutputFormat,
        config.defaultLanguage,
        gson,
        apiHeader
    )

    @Provides
    @Singleton
    fun provideFeedbackApi(
        feedbackService: FeedbackService,
        requestMapper: FeedbackRequestMapper,
        apiHeader: ApiHeader,
    ): IFeedbackApi = FeedbackApi(
        feedbackService,
        requestMapper,
        apiHeader
    )
}
