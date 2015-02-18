package org.springframework.test.web.portlet.server.request;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.portlet.MockClientDataRequest;
import org.springframework.util.Assert;

public class MockClientDataRequestBuilder extends MockPortletRequestBuilder {

    private HttpMethod method = HttpMethod.GET;

    protected void setMethod(HttpMethod method) {
        Assert.notNull(method, "'method' is required");
        this.method = method;
    }
    
    protected void setAll(MockClientDataRequest request) {
        super.setAll(request);
        request.setMethod(method.name());
    }    
}