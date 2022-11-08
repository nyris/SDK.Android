package io.nyris.sdk;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * ImageMatchingApiTest.java - Unit tests for the implementation of {@link IImageMatchingApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
public class ImageMatchingApiTest extends BaseTest {
    @Mock
    private ImageMatchingService imageMatchingService;
    private ImageMatchingApi imageMatchingApi;
    private Gson gson;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        gson = new Gson();
        imageMatchingApi = new ImageMatchingApi(
                imageMatchingService,
                "OUTPUT_FORMAT",
                "OUTPUT_FORMAT",
                gson,
                sdkScheduler,
                apiHeader,
                endpoints
        );
    }

    @Test
    public void match_shouldReturnCorrectOfferResponseBody() {
        // Get an instance of OfferResponseBody
        OfferResponseBody offerResponseBody = getOfferResponseBody();
        ResponseBody responseBody =
                ResponseBody.create(
                        gson.toJson(offerResponseBody, OfferResponseBody.class),
                        MediaType.parse("application/json")
                );

        when(imageMatchingService.match(anyString(), anyMap(), any()))
                .thenReturn(Single.just(responseBody));

        // When Image Matching Api is asked to match a image byte array
        TestObserver<OfferResponseBody> testObserver = imageMatchingApi.match(new byte[]{}).test();

        // Verify match method called once
        verify(imageMatchingService, times(1))
                .match(anyString(), anyMap(), any());

        // Verify matchAndGetRequestId method was not called
        verify(imageMatchingService, times(0))
                .matchAndGetRequestId(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        // Assert the returned offers have the same size of mocked offers
        testObserver.assertValue(r -> r.getOffers().size() == OFFERS_SIZE);
    }

    @Test
    public void match_shouldReturnCorrectJsonResponseBody() {
        // Get an instance of OfferResponseBody
        OfferResponseBody offerResponseBody = getOfferResponseBody();
        ResponseBody responseBody =
                ResponseBody.create(
                        gson.toJson(offerResponseBody, OfferResponseBody.class),
                        MediaType.parse("application/json")
                );

        when(imageMatchingService.match(anyString(), anyMap(), any()))
                .thenReturn(Single.just(responseBody));

        // When Image Matching Api is asked to match a image byte array and asked to return
        // response as JsonResponseBody
        TestObserver<JsonResponseBody> testObserver = imageMatchingApi
                .match(new byte[]{}, JsonResponseBody.class)
                .test();

        // Verify match method was not called
        verify(imageMatchingService, times(1))
                .match(anyString(), anyMap(), any());

        // Verify matchAndGetRequestId method was once
        verify(imageMatchingService, times(0))
                .matchAndGetRequestId(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        // Assert the returned json string is the same of mocked offers
        testObserver.assertValue(r -> Objects.equals(r.getJson(), gson.toJson(offerResponseBody)));
    }

    @Test
    public void match_shouldReturnCorrectOfferResponse() {
        // Get an instance of OfferResponseBody
        Response<OfferResponseBody> response = Response.success(getOfferResponseBody());
        when(imageMatchingService.matchAndGetRequestId(anyString(), anyMap(), any()))
                .thenReturn(Single.just(response));

        // When Image Matching Api is asked to match an image byte array and asked to return
        // response as OfferResponse
        TestObserver<OfferResponse> testObserver = imageMatchingApi
                .match(new byte[]{}, OfferResponse.class)
                .test();

        // Verify match method was not called
        verify(imageMatchingService, times(0))
                .match(anyString(), anyMap(), any());

        // Verify matchAndGetRequestId method was once
        verify(imageMatchingService, times(1))
                .matchAndGetRequestId(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        // Assert the returned offers have the same size of mocked offers
        testObserver.assertValue(r ->
                Objects.requireNonNull(r.getBody()).getOffers().size() == OFFERS_SIZE
        );
    }

    @Test
    public void match_shouldTerminatedWithHttpError() {
        // Get an instance of HttpException
        HttpException httpException = new HttpException(
                Response.error(
                        403,
                        ResponseBody.create("Forbidden", MediaType.parse("application/json"))
                )
        );
        when(imageMatchingService.match(anyString(), anyMap(), any()))
                .thenReturn(Single.error(httpException));

        // When Image Matching Api is asked to match an image byte array
        TestObserver<OfferResponseBody> testObserver = imageMatchingApi.match(new byte[]{}).test();

        // Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void match_shouldTerminatedWithIOError() {
        // Get an instance of IOException
        when(imageMatchingService.match(anyString(), anyMap(), any()))
                .thenReturn(Single.error(new IOException()));

        // When Image Matching Api is asked to match an image byte array
        TestObserver<OfferResponseBody> testObserver = imageMatchingApi
                .match(new byte[]{})
                .test();

        // Then
        testObserver.assertError(IOException.class);
    }

}
