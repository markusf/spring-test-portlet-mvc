package org.springframework.test.web.portlet.server.result;

import static org.springframework.test.web.portlet.server.result.PortletMockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.portlet.server.DefaultPortletMvcResult;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.web.portlet.ModelAndView;
import static org.hamcrest.Matchers.*;

public class ViewResultMatchersTests {
	
	@Test
	public void testViewNameEquals() throws Exception {
		PortletMvcResult result = getPortletMvcResultWithViewName("hello");
		
		view().name("hello").match(result);
	}
	
	@Test(expected = AssertionError.class)
	public void testViewNameEqualsFail() throws Exception {
		PortletMvcResult result = getPortletMvcResultWithViewName("world");
		
		view().name("hello").match(result);
	}
	
	@Test
	public void testViewNameMatcher() throws Exception {
		PortletMvcResult result = getPortletMvcResultWithViewName("hello");
		
		view().name(is("hello")).match(result);
	}
	
	@Test(expected = AssertionError.class)
	public void testViewNameMatcherFail() throws Exception {
		PortletMvcResult result = getPortletMvcResultWithViewName("world");
		
		view().name(is("hello")).match(result);
	}
	
	private PortletMvcResult getPortletMvcResultWithViewName(String viewName) {
		DefaultPortletMvcResult result = new DefaultPortletMvcResult(null, null);
		
		ModelAndView mav = new ModelAndView(viewName);
		
		result.setModelAndView(mav);
		
		return result;
	}
	
}
