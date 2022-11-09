package io.nyris.sdk;

import org.junit.Before;
import org.junit.ClassRule;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

/**
 * BaseTest.java - Base Unit Test class
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
public class BaseTest {
    @ClassRule
    public static final TestSchedulerRule testSchedulerRule = new TestSchedulerRule();
    final int OFFERS_SIZE = 10;
    SdkSchedulerProvider sdkScheduler;
    ApiHeader apiHeader;
    EndpointBuilder endpoints;

    @Before
    public void setUp() {
        // Mock fields
        MockitoAnnotations.openMocks(this);

        // Create instance of SdkScheduler
        sdkScheduler = new SdkSchedulerProvider();

        // Create dummy ApiHeader
        apiHeader = new ApiHeader(
                "API_KEY",
                "APPLICATION_ID",
                "SDK_VERSION",
                "GIT_COMMIT_HASH",
                "ANDROID_VERSION"
        );

        // Create dummy endpoints
        endpoints = new EndpointBuilder(
                "SCHEME",
                "HOST_URL",
                "API_VERSION"
        );
    }

    /**
     * Get Offer Response Body : Get dummy offerResponse
     *
     * @return OfferResponseBody instance
     * @see OfferResponse
     */
    OfferResponse getOfferResponseBody() {
        OfferResponse offerResponse = new OfferResponse();
        offerResponse.offers = new ArrayList<>();
        for (int i = 0; i < OFFERS_SIZE; i++) {
            offerResponse.getOffers().add(new Offer());
        }
        return offerResponse;
    }

}
