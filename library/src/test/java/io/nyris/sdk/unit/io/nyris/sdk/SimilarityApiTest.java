package io.nyris.sdk;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Objects;

import io.nyris.sdk.ISimilarityApi;
import io.nyris.sdk.JsonResponseBody;
import io.nyris.sdk.OfferResponseBody;
import io.nyris.sdk.SimilarityApi;
import io.nyris.sdk.SimilarityService;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * SimilarityApiTest.java - Unit tests for the implementation of {@link ISimilarityApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

@SuppressWarnings("KotlinInternalInJava")
public class SimilarityApiTest extends BaseTest {
    @Mock
    private SimilarityService similarityService;

    private SimilarityApi similarityApi;

    private Gson gson;

    private final String sku = "sku";
    @Before
    @Override
    public void setUp(){
        super.setUp();

        gson = new Gson();

        similarityApi = new SimilarityApi(similarityService,
                "OUTPUT_FORMAT",
                "OUTPUT_FORMAT",
                gson,
                sdkScheduler,
                apiHeader,
                endpoints);
    }

    @Test
    public void getBySku_shouldReturnOfferResponseBody(){
        //Get an instance of OfferResponseBody
        OfferResponseBody offerResponseBody = getOfferResponseBody();
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"),
                gson.toJson(offerResponseBody, OfferResponseBody.class));

        when(similarityService.getBySku(anyString(), anyMap()))
                .thenReturn(Single.just(responseBody));

        //When Similarity Api is asked to give similar offers based on sku
        TestObserver<OfferResponseBody> testObserver = similarityApi
                .getBySku(sku)
                .test();

        //verify match method called once
        verify(similarityService,times(1)).getBySku(anyString(), anyMap());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.getOffers().size() == OFFERS_SIZE);
    }

    @Test
    public void getBySku_shouldReturnJsonResponseBody(){
        //Get an instance of OfferResponseBody
        OfferResponseBody offerResponseBody = getOfferResponseBody();
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"),
                gson.toJson(offerResponseBody, OfferResponseBody.class));

        when(similarityService.getBySku(anyString(), anyMap()))
                .thenReturn(Single.just(responseBody));

        //When Similarity Api is asked to give similar offers based on sku
        TestObserver<JsonResponseBody> testObserver = similarityApi
                .getBySku(sku, JsonResponseBody.class)
                .test();

        //verify match method was not called
        verify(similarityService,times(1)).getBySku(anyString(), anyMap());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.getJson().equals(gson.toJson(offerResponseBody)));
    }

    @Test
    public void getBySku_shouldTerminatedWithHttpError() {
        //Get an instance of HttpException
        HttpException httpException = new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"),
                        "Forbidden")));
        //Given
        when(similarityService.getBySku(anyString(), anyMap()))
                .thenReturn(Single.error(httpException));

        //When Similarity Api is asked to give similar offers based on sku
        TestObserver<JsonResponseBody> testObserver = similarityApi
                .getBySku(sku, JsonResponseBody.class)
                .test();

        //Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void getBySku_shouldTerminatedWithIOError() {
        //Get an instance of IOException
        when(similarityService.getBySku(anyString(), anyMap()))
                .thenReturn(Single.error(new IOException()));

        //When Similarity Api is asked to give similar offers based on sku
        TestObserver<JsonResponseBody> testObserver = similarityApi
                .getBySku(sku, JsonResponseBody.class)
                .test();

        //Then
        testObserver.assertError(IOException.class);
    }
}
