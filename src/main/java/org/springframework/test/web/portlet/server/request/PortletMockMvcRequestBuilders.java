package org.springframework.test.web.portlet.server.request;

public abstract class PortletMockMvcRequestBuilders {

	public static MockRenderRequestBuilder render() {
		return new MockRenderRequestBuilder();
	}
	
	public static MockActionRequestBuilder action() {
		return new MockActionRequestBuilder();
	}
	
	public static MockResourceRequestBuilder resource() {
		return new MockResourceRequestBuilder();
	}
}
