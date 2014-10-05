package org.springframework.test.web.portlet.server.samples;

import static org.springframework.test.web.portlet.server.request.PortletMockMvcRequestBuilders.*;
import static org.springframework.test.web.portlet.server.setup.PortletMockMvcBuilders.*;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.Person;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class BasicTests {
	
	@Test
	public void testBasicPortlet() throws Exception {
		existingApplicationContext(null).build()
			.performRender(render().param("test", "test"))
				.andReturn();
	}
	
	@Controller
	@RequestMapping("VIEW")
	private static class PersonController {
		
		@RequestMapping
		public String view(RenderRequest request, RenderResponse response, Model model) {
			Person person = new Person();
			model.addAttribute(person);
			return "view";
		}
		
	}
	
}
