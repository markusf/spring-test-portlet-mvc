package org.springframework.test.web.portlet.server.request;

import org.springframework.mock.web.portlet.MockActionRequest;

public class MockActionRequestBuilder extends MockPortletRequestBuilder implements
        ActionRequestBuilder {

    public MockActionRequestBuilder param(String name, String... values) {
        addParameter(name, values);
        return this;
    }

    public MockActionRequest buildRequest() {
        MockActionRequest request = new MockActionRequest();
        setParameters(request);
        return request;
    }
}
