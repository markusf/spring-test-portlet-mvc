package org.springframework.test.web.portlet.server.request;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.portlet.MockResourceRequest;

public class MockResourceRequestBuilder extends MockClientDataRequestBuilder implements
        ResourceRequestBuilder {

    public MockResourceRequestBuilder param(String name, String... values) {
        addParameter(name, values);
        return this;
    }

    public MockResourceRequestBuilder mode(PortletMode portletMode) {
        setPortletMode(portletMode);
        return this;
    }

    public MockResourceRequestBuilder windowState(WindowState windowState) {
        setWindowState(windowState);
        return this;
    }

    public MockResourceRequestBuilder method(HttpMethod method) {
        setMethod(method);
        return this;
    }

    public MockResourceRequest buildRequest() {
        MockResourceRequest request = new MockResourceRequest();
        setAll(request);
        return request;
    }

}
