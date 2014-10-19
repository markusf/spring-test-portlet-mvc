package org.springframework.test.web.portlet.server.result;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceResponse;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.portlet.MockResourceRequest;
import org.springframework.mock.web.portlet.MockResourceResponse;
import org.springframework.test.web.portlet.server.DefaultPortletMvcResult;
import org.springframework.test.web.portlet.server.PortletMvcResult;

import static org.junit.Assert.*;
import static org.springframework.test.web.portlet.server.result.StatusResultMatchers.*;

public class StatusResultMatcherTests {
	
	@Test
	public void testStatusOK() throws Exception {
		List<AssertionError> assertionErrors = new ArrayList<AssertionError>();
		
		try {
			status().isOk().match(getPortletMvcResultWithStatus(HttpStatus.OK));
		} catch (AssertionError e) {
			assertionErrors.add(e);
		}
		
		assertEquals(0, assertionErrors.size());
		
		try {
			status().isOk().match(getPortletMvcResultWithStatus(null));
		} catch (AssertionError e) {
			assertionErrors.add(e);
		}
		
		assertEquals(1, assertionErrors.size());
	}
	
	@Test
	public void testAnyStatus() throws Exception {
		List<AssertionError> assertionErrors = new ArrayList<AssertionError>();
		
		try {
			status().matches(HttpStatus.BAD_REQUEST).match(getPortletMvcResultWithStatus(HttpStatus.BAD_REQUEST));
		} catch (AssertionError e) {
			assertionErrors.add(e);
		}
		
		assertEquals(0, assertionErrors.size());
		
		try {
			status().matches(HttpStatus.BAD_REQUEST).match(getPortletMvcResultWithStatus(null));
		} catch (AssertionError e) {
			assertionErrors.add(e);
		}
		
		assertEquals(1, assertionErrors.size());
	}
	
	private PortletMvcResult getPortletMvcResultWithStatus(HttpStatus status) {
		MockResourceRequest request = new MockResourceRequest();
		MockResourceResponse response = new MockResourceResponse();
		
		if (status != null) {
			response.setProperty(ResourceResponse.HTTP_STATUS_CODE, String.valueOf(status.value()));
		}
		
		return new DefaultPortletMvcResult(request, response);
	}
	
}
