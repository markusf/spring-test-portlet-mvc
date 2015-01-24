package org.springframework.test.web.portlet.server.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.ReadOnlyException;
import javax.portlet.WindowState;

import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class MockPortletRequestBuilder {

    private final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
    private final MultiValueMap<String, String> preferences = new LinkedMultiValueMap<String, String>();

    private final Map<String, Object> sessionPortletAttributes = new HashMap<String, Object>();
    private final Map<String, Object> sessionApplicationAttributes = new HashMap<String, Object>();

    private PortletMode portletMode = PortletMode.VIEW;
    private WindowState windowState = WindowState.NORMAL;

    protected void addParameter(String name, String... values) {
        addToMultiValueMap(this.parameters, name, values);
    }

    protected void addPreference(String name, String... values) {
        addToMultiValueMap(this.preferences, name, values);
    }

    protected void setPortletMode(PortletMode portletMode) {
        Assert.notNull(portletMode, "'portletMode' is required");
        this.portletMode = portletMode;
    }

    protected void setWindowState(WindowState windowState) {
        Assert.notNull(windowState, "'windowState' is required");
        this.windowState = windowState;
    }

    protected void addSessionPortletAttributes(Map<String, Object> sessionPortletAttributes) {
        this.sessionPortletAttributes.putAll(sessionPortletAttributes);
    }

    protected void addSessionApplicationAttributes(Map<String, Object> sessionApplicationAttributes) {
        this.sessionApplicationAttributes.putAll(sessionApplicationAttributes);
    }

    protected void setAll(MockPortletRequest request) {
        setParameters(request);
        setPreferences(request);
        request.setPortletMode(this.portletMode);
        request.setWindowState(this.windowState);
        if(!sessionPortletAttributes.isEmpty() || !sessionApplicationAttributes.isEmpty() ) {
            PortletSession session = request.getPortletSession();
            addAttributes(session, sessionPortletAttributes, PortletSession.PORTLET_SCOPE);
            addAttributes(session, sessionApplicationAttributes, PortletSession.APPLICATION_SCOPE);
        }
    }

    private void addAttributes(PortletSession session, Map<String, Object> attributes, int scope) {
        for(String attributeName: attributes.keySet()) {
            session.setAttribute(attributeName, attributes.get(attributeName), scope);
        }
    }

    private void setPreferences(MockPortletRequest request) {
        PortletPreferences preferences = request.getPreferences();
        try {
            for (String name : this.preferences.keySet()) {
                List<String> values = this.preferences.get(name);
                preferences.setValues(name, values.toArray(new String[] {}));
            }
        } catch (ReadOnlyException e) {
            throw new IllegalArgumentException("Read only preferences object is not supported", e);
        }
    }

    private void setParameters(MockPortletRequest request) {
        for (String name : this.parameters.keySet()) {
            List<String> params = this.parameters.get(name);
            request.addParameter(name, params.toArray(new String[] {}));
        }
    }

    private static <T> void addToMultiValueMap(MultiValueMap<String, T> map, String name, T[] values) {
        Assert.hasLength(name, "'name' must not be empty");
        Assert.notNull(values, "'values' is required");
        Assert.notEmpty(values, "'values' must not be empty");
        for (T value : values) {
            map.add(name, value);
        }
    }
}
