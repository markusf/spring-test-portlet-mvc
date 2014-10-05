package org.springframework.test.web.portlet.server.setup;

import org.springframework.context.ApplicationContext;
import org.springframework.test.web.portlet.server.DefaultPortletMockMvcBuilder;

public class PortletMockMvcBuilders {
	
	public static PortletMockMvcBuilder existingApplicationContext(ApplicationContext applicationContext) {
		return new DefaultPortletMockMvcBuilder().setApplicationContext(applicationContext);
	}
	
}
