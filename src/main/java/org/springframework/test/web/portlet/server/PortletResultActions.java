package org.springframework.test.web.portlet.server;

public interface PortletResultActions {
	
	PortletResultActions andExpect(PortletResultMatcher matcher) throws Exception;

	PortletResultActions andDo(PortletResultHandler handler) throws Exception;

	PortletMvcResult andReturn();
}
