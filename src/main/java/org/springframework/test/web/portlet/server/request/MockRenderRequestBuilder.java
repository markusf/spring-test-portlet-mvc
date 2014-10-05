package org.springframework.test.web.portlet.server.request;

import org.springframework.mock.web.portlet.MockRenderRequest;

public class MockRenderRequestBuilder extends MockPortletRequestBuilder implements RenderRequestBuilder {
	
	public MockRenderRequestBuilder param(String name, String... values) {
		// TODO Auto-generated method stub
		addParameter(name, values);
		return this;
	}
	
	public MockRenderRequest buildRequest() {
		MockRenderRequest request = new MockRenderRequest();
		
		setParameters(request);
		
		// TODO Auto-generated method stub
		return request;
	}

}
