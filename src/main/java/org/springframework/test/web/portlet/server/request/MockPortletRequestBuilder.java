package org.springframework.test.web.portlet.server.request;

import java.util.List;

import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class MockPortletRequestBuilder {
	
	private final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	
	protected void addParameter(String name, String... values) {
		addToMultiValueMap(this.parameters, name, values);
	}
	
	protected void setParameters(MockPortletRequest request) {
		for (String name : this.parameters.keySet()) {
			List<String> params = this.parameters.get(name);
			request.addParameter(name, params.toArray(new String[]{}));
		}
	}
	
	private static <T> void addToMultiValueMap(MultiValueMap<String, T> map, String name, T[] values) {
		Assert.hasLength(name, "'name' must not be empty");
		Assert.notNull(values, "'values' is required");
		Assert.notEmpty(values, "'values' must not be empty");
		for (T value : values) {
			map.add(name, value);
		}
	}
}
