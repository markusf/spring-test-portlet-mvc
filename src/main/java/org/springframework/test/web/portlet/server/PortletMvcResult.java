package org.springframework.test.web.portlet.server;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.springframework.web.portlet.HandlerInterceptor;
import org.springframework.web.portlet.ModelAndView;

public interface PortletMvcResult {
	
	PortletRequest getRequest();
	
	PortletResponse getResponse();
	
	/**
	 * Return the {@code ModelAndView} prepared by the handler.
	 * @return a {@code ModelAndView}, or {@code null}
	 */
	ModelAndView getModelAndView();

	/**
	 * Return any exception raised by a handler and successfully resolved
	 * through a {@link HandlerExceptionResolver}.
	 *
	 * @return an exception, possibly {@code null}
	 */
 	Exception getResolvedException();
 	
	/**
	 * Return the executed handler.
	 * @return the handler, possibly {@code null} if none were executed
	 */
	Object getHandler();

	/**
	 * Return interceptors around the handler.
	 * @return interceptors, or {@code null} if none were selected
	 */
	HandlerInterceptor[] getInterceptors();
}
