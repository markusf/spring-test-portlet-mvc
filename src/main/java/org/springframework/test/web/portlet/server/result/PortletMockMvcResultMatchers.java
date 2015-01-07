package org.springframework.test.web.portlet.server.result;

public final class PortletMockMvcResultMatchers {
	
	public static StatusResultMatchers status() {
		return new StatusResultMatchers();
	}
	
	public static ModelResultMatchers model() {
		return new ModelResultMatchers();
	}
	
	public static ViewResultMatchers view() {
		return new ViewResultMatchers();
	}
	
    public static RenderParametersResultMatchers renderParameters() {
        return new RenderParametersResultMatchers();
    }
}
