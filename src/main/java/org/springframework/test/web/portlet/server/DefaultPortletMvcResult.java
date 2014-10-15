package org.springframework.test.web.portlet.server;

import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockPortletResponse;
import org.springframework.web.portlet.HandlerInterceptor;
import org.springframework.web.portlet.ModelAndView;

public class DefaultPortletMvcResult implements PortletMvcResult {
	
	private final MockPortletRequest request;
	
	private final MockPortletResponse response;
	
	private ModelAndView modelAndView;

	private Exception resolvedException;
	
	private Object handler;
	
	private HandlerInterceptor[] interceptors;
	
	public DefaultPortletMvcResult(MockPortletRequest request, MockPortletResponse response) {
		this.request = request;
		this.response = response;
	}

	public MockPortletRequest getRequest() {
		return request;
	}

	public MockPortletResponse getResponse() {
		return response;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	public Exception getResolvedException() {
		return resolvedException;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public void setResolvedException(Exception resolvedException) {
		this.resolvedException = resolvedException;
	}
	
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	
	public Object getHandler() {
		return handler;
	}
	
	public void setInterceptors(HandlerInterceptor[] interceptors) {
		this.interceptors = interceptors;
	}
	
	public HandlerInterceptor[] getInterceptors() {
		return interceptors;
	}
	
}
