package org.springframework.test.web.portlet.server;

public interface PortletResultHandler {
	
	void handle(PortletMvcResult result) throws Exception;
	
}
