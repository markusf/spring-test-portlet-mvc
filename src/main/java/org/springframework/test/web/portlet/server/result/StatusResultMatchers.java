package org.springframework.test.web.portlet.server.result;

import static org.springframework.test.web.AssertionErrors.assertEquals;

import javax.portlet.ResourceResponse;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.test.web.portlet.server.PortletResultMatcher;

public class StatusResultMatchers {
	
	protected StatusResultMatchers() {
		// TODO Auto-generated constructor stub
	}
	
	public static StatusResultMatchers status() {
		return new StatusResultMatchers();
	}
	
    public PortletResultMatcher isOk() {
        return matcher(HttpStatus.OK);
    }
    
    public PortletResultMatcher matches(HttpStatus httpStatus) {
    	return matcher(httpStatus);
    }
    
    private PortletResultMatcher matcher(final HttpStatus status) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) {
				String statusInRequest = result.getResponse().getProperty(ResourceResponse.HTTP_STATUS_CODE);
				
				Integer intStatus = statusInRequest != null ? Integer.valueOf(statusInRequest) : null;
				
				assertEquals("Status", status.value(), intStatus);
			}
		};
	}
	
	
}
