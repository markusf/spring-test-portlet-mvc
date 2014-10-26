package org.springframework.test.web.portlet.server.samples;

import static org.springframework.test.web.portlet.server.request.PortletMockMvcRequestBuilders.render;
import static org.springframework.test.web.portlet.server.setup.PortletMockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.portlet.server.result.PortletMockMvcResultMatchers.*;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

public class StandaloneAppContextTest {
	
	@Test
	public void testStandaloneAppContext() throws Exception {
		MyFirstController controller = new MyFirstController();
		MyInterceptor interceptor = new MyInterceptor();
		StubValidator validator = new StubValidator();
		
		standaloneSetup(controller)
			.addInteceptors(interceptor)
			.setValidator(validator)
			.build()
			.perform(render().param("test", "test"))
			.andExpect(model().attribute("foo", "bar"))
			.andExpect(model().attribute("intercepted", "yes"))
			// this tests, if called controller is the instance, which we instantiated 
			// (and not a new instance of it, which would be instantiated by spring)
			.andExpect(model().attribute("controller", controller))
			// this tests, if called interceptor is the instance, which we instantiated 
			//(and not a new instance of it, which would be instantiated by spring)
			.andExpect(model().attribute("interceptor", interceptor))
			.andReturn();
	}
	
	@Test
	public void testStandaloneAppContextControllerOnly() throws Exception {
		MyFirstController controller = new MyFirstController();
		
		standaloneSetup(controller)
			.build()
			.perform(render().param("test", "test"))
			.andExpect(model().attribute("foo", "bar"))
			.andReturn();
	}
	
	@Controller
	@RequestMapping("VIEW")
	private static class MyFirstController {
		
		@RenderMapping
		public String render(Model model) {
			model.addAttribute("foo", "bar");
			model.addAttribute("controller", this);
			
			return "view";
		}
		
	}
	
	private static class MyInterceptor extends HandlerInterceptorAdapter {
		
		@Override
		public void postHandleRender(RenderRequest request,
				RenderResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {
			
			modelAndView.addObject("intercepted", "yes");
			modelAndView.addObject("interceptor", this);
		}
		
	}
	
	private static class StubValidator implements Validator {

		public boolean supports(Class<?> arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		public void validate(Object arg0, Errors arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
