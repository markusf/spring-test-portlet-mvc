package org.springframework.test.web.portlet.server.request;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.springframework.mock.web.portlet.MockActionRequest;

public class MockActionRequestBuilder extends MockPortletRequestBuilder implements
        ActionRequestBuilder {

    public MockActionRequestBuilder param(String name, String... values) {
        addParameter(name, values);
        return this;
    }

    public MockActionRequestBuilder preferences(String name, String... values) {
        addPreference(name, values);
        return this;
    }

    public MockActionRequestBuilder attribute(String name, String... values) {
        addAttribute(name, values);
        return this;
    }

    public MockActionRequestBuilder mode(PortletMode portletMode) {
        setPortletMode(portletMode);
        return this;
    }

    public MockActionRequestBuilder windowState(WindowState windowState) {
        setWindowState(windowState);
        return this;
    }

    public MockActionRequest buildRequest() {
        MockActionRequest request = new MockActionRequest();
        setAll(request);
        return request;
    }
}
