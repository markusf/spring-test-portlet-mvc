package org.springframework.test.web.portlet.server;

public interface PortletResultActions {
	
	/**
	 * Provide an expectation. For example:
	 * <pre>
	 * static imports: MockMvcRequestBuilders.*, MockMvcResultMatchers.*
	 *
	 * mockMvc.perform(get("/person/1"))
	 *   .andExpect(status().isOk())
	 *   .andExpect(content().mimeType(MediaType.APPLICATION_JSON))
	 *   .andExpect(jsonPath("$.person.name").equalTo("Jason"));
	 *
	 * mockMvc.perform(post("/form"))
	 *   .andExpect(status().isOk())
	 *   .andExpect(redirectedUrl("/person/1"))
	 *   .andExpect(model().size(1))
	 *   .andExpect(model().attributeExists("person"))
	 *   .andExpect(flash().attributeCount(1))
	 *   .andExpect(flash().attribute("message", "success!"));
	 * </pre>
	 */
	PortletResultActions andExpect(PortletResultMatcher matcher) throws Exception;

	/**
	 * Provide a general action. For example:
	 * <pre>
	 * static imports: MockMvcRequestBuilders.*, MockMvcResultMatchers.*
	 *
	 * mockMvc.perform(get("/form")).andDo(print());
	 * </pre>
	 */
	PortletResultActions andDo(PortletResultHandler handler) throws Exception;

	/**
	 * Return the result of the executed request for direct access to the results.
	 *
	 * @return the result of the request
	 */
	PortletMvcResult andReturn();
}
