package org.springframework.test.web.portlet.server;

import javax.portlet.GenericPortlet;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.mock.web.portlet.MockResourceRequest;
import org.springframework.mock.web.portlet.MockResourceResponse;
import org.springframework.test.web.portlet.server.request.ActionRequestBuilder;
import org.springframework.test.web.portlet.server.request.RenderRequestBuilder;
import org.springframework.test.web.portlet.server.request.ResourceRequestBuilder;

public class PortletMockMvc {
	
	static String MVC_RESULT_ATTRIBUTE = PortletMockMvc.class.getName().concat(".MVC_RESULT_ATTRIBUTE");
	
	private final GenericPortlet portlet;
	
	/**
	 * only accessed via a specific PortletMockMvcBuilder
	 * @param portlet
	 */
	PortletMockMvc(GenericPortlet portlet) {
		this.portlet = portlet;
	}

	public PortletResultActions performRender(RenderRequestBuilder builder) throws Exception {
		MockRenderRequest request = builder.buildRequest();
		MockRenderResponse response = new MockRenderResponse();
		
		PortletMvcResult mvcResult = new DefaultPortletMvcResult(request, response);
		
		request.setAttribute(MVC_RESULT_ATTRIBUTE, mvcResult);
		
		portlet.render(request, response);
		
		return new DefaultResultActions(mvcResult);
	}
	
	public PortletResultActions performAction(ActionRequestBuilder builder) throws Exception {
		MockActionRequest request = builder.buildRequest();
		MockActionResponse response = new MockActionResponse();
		
		PortletMvcResult mvcResult = new DefaultPortletMvcResult(request, response);
		
		request.setAttribute(MVC_RESULT_ATTRIBUTE, mvcResult);
		
		portlet.processAction(request, response);
		
		return new DefaultResultActions(mvcResult);
	}
	
	public PortletResultActions performResource(ResourceRequestBuilder builder) throws Exception {
		MockResourceRequest request = builder.buildRequest();
		MockResourceResponse response = new MockResourceResponse();
		
		PortletMvcResult mvcResult = new DefaultPortletMvcResult(request, response);
		
		request.setAttribute(MVC_RESULT_ATTRIBUTE, mvcResult);
		
		portlet.serveResource(request, response);
		
		return new DefaultResultActions(mvcResult);
	}
	
	private static class DefaultResultActions implements PortletResultActions {
		
		private PortletMvcResult mvcResult;
		
		public DefaultResultActions(PortletMvcResult mvcResult) {
			this.mvcResult = mvcResult;
		}
		
		public PortletResultActions andExpect(PortletResultMatcher matcher)
				throws Exception {
			matcher.match(mvcResult);
			return this;
		}

		public PortletResultActions andDo(PortletResultHandler printer)
				throws Exception {
			printer.handle(mvcResult);
			return this;
		}

		public PortletMvcResult andReturn() {
			return mvcResult;
		}
		
	}
	
	
}
