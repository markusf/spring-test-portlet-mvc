package org.springframework.test.web.portlet.server.request;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.portlet.MockActionRequest;

public class MockActionRequestBuilder extends MockClientDataRequestBuilder implements
        ActionRequestBuilder {

    public MockActionRequestBuilder param(String name, String... values) {
        addParameter(name, values);
        return this;
    }

    public MockActionRequestBuilder preferences(String name, String... values) {
        addPreference(name, values);
        return this;
    }

    public MockActionRequestBuilder attribute(String name, Object value) {
        addAttribute(name, value);
        return this;
    }

    public MockActionRequestBuilder mode(PortletMode mode) {
        setPortletMode(mode);
        return this;
    }

    public MockActionRequestBuilder windowState(WindowState windowState) {
        setWindowState(windowState);
        return this;
    }

    public MockActionRequestBuilder method(HttpMethod method) {
        setMethod(method);
        return this;
    }

    public MockActionRequestBuilder sessionPortletAttribute(String name, Object attribute) {
        addSessionPortletAttribute(name, attribute);
        return this;
    }

    public MockActionRequest buildRequest() {
        MockActionRequest request = new MockActionRequest();
        setAll(request);
        return request;
    }
}
