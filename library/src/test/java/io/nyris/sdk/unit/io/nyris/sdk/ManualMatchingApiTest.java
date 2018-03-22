package io.nyris.sdk;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Objects;

import io.nyris.sdk.IManualMatchingApi;
import io.nyris.sdk.ManualMatchingApi;
import io.nyris.sdk.ManualMatchingService;
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
 * ManualMatchingApiTest.java - Unit tests for the implementation of {@link IManualMatchingApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */
@SuppressWarnings("KotlinInternalInJava")
public class ManualMatchingApiTest extends BaseTest {
    @Mock
    private ManualMatchingService manualMatchingService;

    private ManualMatchingApi manualMatchingApi;

    private final String emptyResponse = "";

    private final String requestId = "requestId";

    @Before
    @Override
    public void setUp(){
        super.setUp();

        manualMatchingApi = new ManualMatchingApi(manualMatchingService,
                sdkScheduler,
                apiHeader,
                endpoints);
    }

    @Test
    public void markForManualMatch_shouldReturnEmptyResponseBody(){
        //Get an instance of ResponseBody
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"),emptyResponse);
        when(manualMatchingService.markForManualMatch(anyString(), anyMap()))
                .thenReturn(Single.just(responseBody));

        //When Manual Matching Api is asked to mark image for manual matching
        TestObserver<ResponseBody> testObserver = manualMatchingApi
                .markForManualMatch(requestId)
                .test();

        //verify markForManualMatch method called once
        verify(manualMatchingService,times(1)).markForManualMatch(anyString(), anyMap());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.string().equals(emptyResponse));
    }

    @Test
    public void markForManualMatch_shouldTerminatedWithHttpError() {
        HttpException httpException = new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"),
                        "Forbidden")));

        //Get an instance of HttpException
        when(manualMatchingService.markForManualMatch(anyString(), anyMap()))
                .thenReturn(Single.error(httpException));

        //When Manual MatchingApi Api is asked to mark image for manual matching
        TestObserver<ResponseBody> testObserver = manualMatchingApi
                .markForManualMatch(requestId)
                .test();

        //Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void markForManualMatch_shouldTerminatedWithIOError() {
        //Get an instance of IOException
        when(manualMatchingService.markForManualMatch(anyString(), anyMap()))
                .thenReturn(Single.error(new IOException()));

        //When Manual MatchingApi Api is asked to mark image for manual matching
        TestObserver<ResponseBody> testObserver = manualMatchingApi
                .markForManualMatch(requestId)
                .test();

        //Then
        testObserver.assertError(IOException.class);
    }
}
