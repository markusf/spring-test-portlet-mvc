package org.springframework.test.web.portlet.server.samples;

import static org.springframework.test.web.portlet.server.request.PortletMockMvcRequestBuilders.*;
import static org.springframework.test.web.portlet.server.result.PortletMockMvcResultMatchers.*;
import static org.springframework.test.web.portlet.server.setup.PortletMockMvcBuilders.*;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml"})	
public class BasicTests {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testBasicPortlet() throws Exception {
		existingApplicationContext(applicationContext).build()
			.perform(render().param("test", "test"))
			.andExpect(view().name("viewPerson"))
			.andExpect(model().attributeExists("person"))
			.andReturn();
	}
	
    @Test
    public void testAction() throws Exception {
        existingApplicationContext(applicationContext)
                .build()
                .perform(action()
                        .mode(PortletMode.VIEW)
                        .windowState(WindowState.NORMAL)
                        .param("action", "createNew"))
                .andExpect(model().attributeExists("person")).andExpect(preferences().count(0))
                .andExpect(renderParameters().count(1))
                .andExpect(renderParameters().exists("view"))
                .andExpect(renderParameters().has("view", "editPersonForm")).andReturn();
    }
}