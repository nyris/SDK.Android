package io.nyris.sdk;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * RegionsApiTest.java - Unit tests for the implementation of {@link IRegionsApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

public class RegionsApiTest extends BaseTest {
    private final int OBJECTS_SIZE = 10;
    @Mock
    private RegionsService objectProposalService;
    private RegionsApi objectProposalApi;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        objectProposalApi = new RegionsApi(
                objectProposalService,
                sdkScheduler,
                apiHeader,
                endpoints
        );
    }

    private List<Object> getObjectProposalList() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < OBJECTS_SIZE; i++) {
            list.add(new Object());
        }
        return list;
    }

    @Test
    public void extractObjects_shouldReturnOfferResponseBody() {
        // Get an instance of List of ObjectProposal
        List<Object> objectProposals = getObjectProposalList();
        when(objectProposalService.detect(anyString(), anyMap(), any()))
                .thenReturn(Single.just(objectProposals));

        // When Object Proposal Api is asked to extract object from image byte array
        TestObserver<List<Object>> testObserver = objectProposalApi
                .detect(new byte[]{})
                .test();

        // Verify extractObjects method called once
        verify(objectProposalService, times(1))
                .detect(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.size() == OBJECTS_SIZE);
    }

    @Test
    public void extractObjects_shouldTerminatedWithHttpError() {
        HttpException httpException = new HttpException(
                Response.error(
                        403,
                        ResponseBody.create("Forbidden", MediaType.parse("application/json"))
                )
        );

        // Get an instance of HttpException
        when(objectProposalService.detect(anyString(), anyMap(), any()))
                .thenReturn(Single.error(httpException));

        // When Object Proposal Api is asked to extract object from image byte array
        TestObserver<List<Object>> testObserver = objectProposalApi
                .detect(new byte[]{})
                .test();

        // Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void extractObjects_shouldTerminatedWithIOError() {
        // Get an instance of IOException
        when(objectProposalService.detect(anyString(), anyMap(), any()))
                .thenReturn(Single.error(new IOException()));

        // When Object Proposal Api is asked to extract object from image byte array
        TestObserver<List<Object>> testObserver = objectProposalApi
                .detect(new byte[]{})
                .test();

        // Then
        testObserver.assertError(IOException.class);
    }

}
