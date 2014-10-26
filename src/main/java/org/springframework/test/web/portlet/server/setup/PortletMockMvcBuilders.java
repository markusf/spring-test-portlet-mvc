package org.springframework.test.web.portlet.server.setup;

import org.springframework.context.ApplicationContext;
import org.springframework.test.web.portlet.server.DefaultPortletMockMvcBuilder;
import org.springframework.test.web.portlet.server.StandalonePortletMockMvcBuilder;

public class PortletMockMvcBuilders {
	
	public static PortletMockMvcBuilder existingApplicationContext(ApplicationContext applicationContext) {
		return new DefaultPortletMockMvcBuilder().setApplicationContext(applicationContext);
	}
	
	public static StandalonePortletMockMvcBuilder standaloneSetup(Object... controllers) {
		return new StandalonePortletMockMvcBuilder(controllers);
	}
}
