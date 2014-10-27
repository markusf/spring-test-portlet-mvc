package org.springframework.test.web.portlet.server;

import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockPortletResponse;
import org.springframework.web.portlet.HandlerInterceptor;
import org.springframework.web.portlet.ModelAndView;

public interface PortletMvcResult {
	
	MockPortletRequest getRequest();
	
	MockPortletResponse getResponse();
	
	ModelAndView getModelAndView();

 	Exception getResolvedException();
 	
	Object getHandler();

	HandlerInterceptor[] getInterceptors();
}
