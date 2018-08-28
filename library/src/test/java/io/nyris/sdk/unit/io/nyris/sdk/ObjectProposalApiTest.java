package io.nyris.sdk;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ObjectProposalApiTest.java - Unit tests for the implementation of {@link IObjectProposalApi}.
 *
 * @author Sidali Mellouk
 * Created by nyris GmbH
 * Copyright © 2018 nyris GmbH. All rights reserved.
 */

@SuppressWarnings("KotlinInternalInJava")
public class ObjectProposalApiTest extends BaseTest {
    private final int OBJECTS_SIZE = 10;
    @Mock
    private ObjectProposalService objectProposalService;
    private ObjectProposalApi objectProposalApi;

    @Before
    @Override
    public void setUp() {
        super.setUp();

        objectProposalApi = new ObjectProposalApi(objectProposalService,
                sdkScheduler,
                apiHeader,
                endpoints);
    }

    private List<ObjectProposal> getObjectProposalList() {
        List<ObjectProposal> list = new ArrayList<>();
        for (int i = 0; i < OBJECTS_SIZE; i++) {
            list.add(new ObjectProposal());
        }
        return list;
    }

    @Test
    public void extractObjects_shouldReturnOfferResponseBody() {
        //Get an instance of List of ObjectProposal
        List<ObjectProposal> objectProposals = getObjectProposalList();
        when(objectProposalService.extractObjects(anyString(), anyMap(), any()))
                .thenReturn(Single.just(objectProposals));

        //When Object Proposal Api is asked to extract object from image byte array
        TestObserver<List<ObjectProposal>> testObserver = objectProposalApi
                .extractObjects(new byte[]{})
                .test();

        //verify extractObjects method called once
        verify(objectProposalService, times(1)).extractObjects(anyString(), anyMap(), any());

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(Objects::nonNull);
        testObserver.assertValue(r -> r.size() == OBJECTS_SIZE);
    }

    @Test
    public void extractObjects_shouldTerminatedWithHttpError() {
        HttpException httpException = new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"),
                        "Forbidden")));

        //Get an instance of HttpException
        when(objectProposalService.extractObjects(anyString(), anyMap(), any()))
                .thenReturn(Single.error(httpException));

        //When Object Proposal Api is asked to extract object from image byte array
        TestObserver<List<ObjectProposal>> testObserver = objectProposalApi
                .extractObjects(new byte[]{})
                .test();

        //Then
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void extractObjects_shouldTerminatedWithIOError() {
        //Get an instance of IOException
        when(objectProposalService.extractObjects(anyString(), anyMap(), any()))
                .thenReturn(Single.error(new IOException()));

        //When Object Proposal Api is asked to extract object from image byte array
        TestObserver<List<ObjectProposal>> testObserver = objectProposalApi
                .extractObjects(new byte[]{})
                .test();

        //Then
        testObserver.assertError(IOException.class);
    }
}
