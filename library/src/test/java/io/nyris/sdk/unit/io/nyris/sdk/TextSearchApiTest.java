package io.nyris.sdk;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link TextSearchApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

public class TextSearchApiTest extends BaseTest {
    private final String keyword = "keyword";
    @Mock
    private TextSearchService textSearchService;
    private TextSearchApi textSearchApi;
    private Gson gson;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        gson = new Gson();
        textSearchApi = new TextSearchApi(
                textSearchService,
                "OUTPUT_FORMAT",
                "OUTPUT_FORMAT",
                gson,
                sdkScheduler,
                apiHeader,
                endpoints
        );
    }

    @Test
    public void searchOffers_shouldReturnOfferResponseBody() {
        // Get an instance of OfferResponseBody
        OfferResponseBody offerResponseBody = getOfferResponseBody();
        ResponseBody responseBody = ResponseBody.create(
                gson.toJson(offerResponseBody, OfferResponseBody.class),
                MediaType.parse("application/json")
        );

        when(textSearchService.searchOffers(anyString(), anyMap(), any()))
                .thenReturn(Single.just(responseBody));

        // When Text Search Api is asked to search offers using keyword
        TestObserver<OfferResponseBody> testObserver = textSearchApi
                .searchOffers(keyword)
                .test();

        // Verify match method called once
        verify(textSearchService, times(1))
                .searchOffers(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.getOffers().size() == OFFERS_SIZE);
    }

    @Test
    public void searchOffers_shouldReturnJsonResponseBody() {
        // Get an instance of OfferResponseBody
        OfferResponseBody offerResponseBody = getOfferResponseBody();
        ResponseBody responseBody = ResponseBody.create(
                gson.toJson(offerResponseBody, OfferResponseBody.class),
                MediaType.parse("application/json")
        );

        when(textSearchService.searchOffers(anyString(), anyMap(), any()))
                .thenReturn(Single.just(responseBody));

        // When Text Search Api is asked to search offers using keyword
        TestObserver<JsonResponseBody> testObserver = textSearchApi
                .searchOffers(keyword, JsonResponseBody.class)
                .test();

        // Verify match method was not called
        verify(textSearchService, times(1))
                .searchOffers(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> Objects.equals(r.getJson(), gson.toJson(offerResponseBody)));
    }

    @Test
    public void searchOffers_shouldTerminatedWithHttpError() {
        // Get an instance of HttpException
        HttpException httpException = new HttpException(
                Response.error(
                        403,
                        ResponseBody.create("Forbidden", MediaType.parse("application/json"))
                )
        );
        // Given
        when(textSearchService.searchOffers(anyString(), anyMap(), any()))
                .thenReturn(Single.error(httpException));

        // When Text Search Api is asked to search offers using keyword
        TestObserver<OfferResponseBody> testObserver = textSearchApi
                .searchOffers(keyword)
                .test();

        // Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void searchOffers_shouldTerminatedWithIOError() {
        // Get an instance of IOException
        when(textSearchService.searchOffers(anyString(), anyMap(), any()))
                .thenReturn(Single.error(new IOException()));

        // When Text Search Api is asked to search offers using keyword
        TestObserver<OfferResponseBody> testObserver = textSearchApi
                .searchOffers(keyword)
                .test();

        // Then
        testObserver.assertError(IOException.class);
    }

}
