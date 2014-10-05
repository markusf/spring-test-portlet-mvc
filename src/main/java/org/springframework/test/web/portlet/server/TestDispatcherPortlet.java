package org.springframework.test.web.portlet.server;

import java.io.IOException;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.portlet.DispatcherPortlet;
import org.springframework.web.portlet.HandlerExecutionChain;
import org.springframework.web.portlet.ModelAndView;

public class TestDispatcherPortlet extends DispatcherPortlet {
	
	private ApplicationContext context;
	
	public TestDispatcherPortlet(ApplicationContext context) {
		this.context = context;
	}
	
	@Override
	protected ApplicationContext createPortletApplicationContext(
			ApplicationContext parent) {
		return context;
	}
	
	protected DefaultPortletMvcResult getMvcResult(PortletRequest request) {
		return (DefaultPortletMvcResult) request.getAttribute(PortletMockMvc.MVC_RESULT_ATTRIBUTE);
	}
	
	@Override
	protected void render(ModelAndView mv, PortletRequest request,
			MimeResponse response) throws Exception {
		// TODO Auto-generated method stub
		DefaultPortletMvcResult mvcResult = getMvcResult(request);
		mvcResult.setModelAndView(mv);
		
		super.render(mv, request, response);
	}
	
	@Override
	protected HandlerExecutionChain getHandler(PortletRequest request)
			throws Exception {
		HandlerExecutionChain chain = super.getHandler(request);
		if (chain != null) {
			DefaultPortletMvcResult mvcResult = getMvcResult(request);
			mvcResult.setHandler(chain.getHandler());
			mvcResult.setInterceptors(chain.getInterceptors());
		}
		return chain;
	}
	
	@Override
	protected ModelAndView processHandlerException(RenderRequest request,
			RenderResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mav = super.processHandlerException(request, response, handler, ex);
		
		processHandlerException(request, mav, ex);
		
		return mav;
	}
	
	@Override
	protected ModelAndView processHandlerException(ResourceRequest request,
			ResourceResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mav =  super.processHandlerException(request, response, handler, ex);
		
		processHandlerException(request, mav, ex);
		
		return mav;
	}
	
	private void processHandlerException(PortletRequest request, ModelAndView mav, Exception e) {
		DefaultPortletMvcResult mvcResult = getMvcResult(request);
		mvcResult.setResolvedException(e);
		mvcResult.setModelAndView(mav);
	}
	
	
}
