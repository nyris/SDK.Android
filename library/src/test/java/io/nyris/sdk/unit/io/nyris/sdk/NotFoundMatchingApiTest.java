package io.nyris.sdk;

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

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * NotFoundMatchingApiTest.java - Unit tests for the implementation of {@link INotFoundMatchingApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@SuppressWarnings("KotlinInternalInJava")
public class NotFoundMatchingApiTest extends BaseTest {
    private final String emptyResponse = "";
    private final String requestId = "requestId";
    @Mock
    private NotFoundMatchingService notFoundMatchingService;
    private NotFoundMatchingApi notFoundMatchingApi;

    @Before
    @Override
    public void setUp() {
        super.setUp();

        notFoundMatchingApi = new NotFoundMatchingApi(notFoundMatchingService,
                sdkScheduler,
                apiHeader,
                endpoints);
    }

    @Test
    public void markAsNotFound_shouldReturnEmptyResponseBody() {
        //Get an instance of ResponseBody
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"), emptyResponse);
        when(notFoundMatchingService.markAsNotFound(anyString(), anyMap()))
                .thenReturn(Single.just(responseBody));

        //When Manual Matching Api is asked to mark image for manual matching
        TestObserver<ResponseBody> testObserver = notFoundMatchingApi
                .markAsNotFound(requestId)
                .test();

        //verify markAsNotFound method called once
        verify(notFoundMatchingService, times(1)).markAsNotFound(anyString(), anyMap());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.string().equals(emptyResponse));
    }

    @Test
    public void markAsNotFound_shouldTerminatedWithHttpError() {
        HttpException httpException = new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"),
                        "Forbidden")));

        //Get an instance of HttpException
        when(notFoundMatchingService.markAsNotFound(anyString(), anyMap()))
                .thenReturn(Single.error(httpException));

        //When Manual MatchingApi Api is asked to mark image for manual matching
        TestObserver<ResponseBody> testObserver = notFoundMatchingApi
                .markAsNotFound(requestId)
                .test();

        //Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void markAsNotFound_shouldTerminatedWithIOError() {
        //Get an instance of IOException
        when(notFoundMatchingService.markAsNotFound(anyString(), anyMap()))
                .thenReturn(Single.error(new IOException()));

        //When Manual MatchingApi Api is asked to mark image for manual matching
        TestObserver<ResponseBody> testObserver = notFoundMatchingApi
                .markAsNotFound(requestId)
                .test();

        //Then
        testObserver.assertError(IOException.class);
    }
}
