package org.springframework.test.web.portlet.server;

import java.util.List;

import javax.portlet.PortletException;

import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.portlet.MockPortletConfig;
import org.springframework.mock.web.portlet.MockPortletContext;
import org.springframework.test.web.portlet.server.setup.PortletMockMvcBuilder;
import org.springframework.util.Assert;

public class DefaultPortletMockMvcBuilder implements PortletMockMvcBuilder {
	
	private ApplicationContext applicationContext;
	
	protected PortletMockMvc createMockMvc(ApplicationContext applicationContext,
			List<PortletResultMatcher> globalResultMatchers, List<PortletResultHandler> globalResultHandlers) {
		
		MockPortletConfig config = new MockPortletConfig(new MockPortletContext(), "test");
		TestDispatcherPortlet portlet = new TestDispatcherPortlet(applicationContext);
		
		try {
			portlet.init(config);
		} catch (PortletException e) {
			throw new RuntimeException("Failed to initialize TestDispatcherPortlet", e);
		}
		
		return new PortletMockMvc(portlet);
	}
	
	public PortletMockMvcBuilder setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		return this;
	}
	
	public PortletMockMvc build() {
		Assert.state(applicationContext != null, "ApplicationContext not provided by concrete PortletMockMvcBuilder");

		return createMockMvc(applicationContext, null, null);
	}

}
