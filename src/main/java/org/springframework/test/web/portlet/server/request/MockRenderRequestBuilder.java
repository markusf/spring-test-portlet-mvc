package org.springframework.test.web.portlet.server.request;

import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;

import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.util.Assert;

public class MockRenderRequestBuilder extends MockPortletRequestBuilder implements
        RenderRequestBuilder {

    public MockRenderRequestBuilder param(String name, String... values) {
        addParameter(name, values);
        return this;
    }

    public MockRenderRequestBuilder mode(PortletMode portletMode) {
        setPortletMode(portletMode);
        return this;
    }

    public MockRenderRequestBuilder windowState(WindowState windowState) {
        setWindowState(windowState);
        return this;
    }

    public MockRenderRequestBuilder copyRenderParameters(PortletMvcResult result) {
        Assert.isInstanceOf(ActionResponse.class, result.getResponse(),
                "Only action requests results are supported");
        ActionResponse actionResponse = (ActionResponse) result.getResponse();
        Map<String, String[]> renderParameterMap = actionResponse.getRenderParameterMap();
        for (String parameterName : renderParameterMap.keySet()) {
            addParameter(parameterName, renderParameterMap.get(parameterName));
        }
        return this;
    }

    public MockRenderRequestBuilder copySessionAttributes(PortletMvcResult result) {
        PortletRequest request = result.getRequest();
        PortletSession session = request.getPortletSession(false);
        if (session != null) {
            addSessionPortletAttributes(session.getAttributeMap(PortletSession.PORTLET_SCOPE));
            addSessionApplicationAttributes(session.getAttributeMap(PortletSession.APPLICATION_SCOPE));
        }
        return this;
    }

    public MockRenderRequest buildRequest() {
        MockRenderRequest request = new MockRenderRequest();
        setAll(request);
        return request;
    }
}