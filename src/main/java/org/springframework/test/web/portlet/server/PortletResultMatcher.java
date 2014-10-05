package org.springframework.test.web.portlet.server;

public interface PortletResultMatcher {
	
	void match(PortletMvcResult result) throws Exception;

}
