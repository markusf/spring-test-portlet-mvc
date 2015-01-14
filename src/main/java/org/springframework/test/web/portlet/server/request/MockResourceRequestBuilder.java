package org.springframework.test.web.portlet.server.request;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.springframework.mock.web.portlet.MockResourceRequest;

public class MockResourceRequestBuilder extends MockPortletRequestBuilder implements
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

    public MockResourceRequest buildRequest() {
        MockResourceRequest request = new MockResourceRequest();
        setAll(request);
        return request;
    }

}
