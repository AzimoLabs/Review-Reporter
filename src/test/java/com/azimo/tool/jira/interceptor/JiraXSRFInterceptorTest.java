package com.azimo.tool.jira.interceptor;

import okhttp3.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import helper.MockHelper;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class JiraXSRFInterceptorTest {

    JiraXSRFInterceptor jiraXSRFInterceptor;
    Request requestMock;

    @Mock
    okhttp3.Interceptor.Chain chainMock;
    @Captor
    ArgumentCaptor<Request> requestCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        requestMock = MockHelper.getRequest();
        jiraXSRFInterceptor = new JiraXSRFInterceptor();
    }

    @Test
    public void whenAppliedInterceptor_shouldApplyXSRFHeader() throws IOException {
        when(chainMock.request()).thenReturn(requestMock);

        jiraXSRFInterceptor.intercept(chainMock);

        verify(chainMock).proceed(requestCaptor.capture());

        assertEquals(requestCaptor.getValue().header(
            JiraXSRFInterceptor.ATLASSIAN_TOKEN_HEADER), JiraXSRFInterceptor.ATLASSIAN_TOKEN_HEADER_VALUE);
    }
}
