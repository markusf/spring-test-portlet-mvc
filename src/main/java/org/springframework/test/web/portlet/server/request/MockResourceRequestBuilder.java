package org.springframework.test.web.portlet.server.request;

import org.springframework.mock.web.portlet.MockResourceRequest;

public class MockResourceRequestBuilder extends MockPortletRequestBuilder implements
        ResourceRequestBuilder {

    public MockResourceRequestBuilder param(String name, String... values) {
        addParameter(name, values);
        return this;
    }

    public MockResourceRequest buildRequest() {
        MockResourceRequest request = new MockResourceRequest();
        setParameters(request);
        return request;
    }

}
