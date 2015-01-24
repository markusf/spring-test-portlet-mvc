/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.web.portlet.server.result;

import static org.springframework.test.web.AssertionErrors.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.test.web.portlet.server.PortletResultMatcher;
import org.springframework.web.portlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class RenderParametersResultMatchers {


	protected RenderParametersResultMatchers() {
	}

	public PortletResultMatcher has(final String name, final Matcher<String> matcher) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
                MatcherAssert.assertThat("Render parameter '" + name + "'",
                        getActionResponse(result).getRenderParameter(name), matcher);
			}
		};
	}

	public PortletResultMatcher has(String name, String value) {
		return has(name, Matchers.equalTo(value));
	}

	public PortletResultMatcher exists(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
				MockActionResponse mockActionResponse = getActionResponse(result);
				for (String name : names) {
				    MatcherAssert.assertThat("Render parameter '" + name + "'",
				            mockActionResponse.getRenderParameterMap().keySet(),
				            Matchers.hasItem(name));
				}
			}
		};
	}

    public PortletResultMatcher doesNotExist(final String... names) {
        return new PortletResultMatcher() {
            public void match(PortletMvcResult portletMvcResult) throws Exception {
                MockActionResponse mockActionResponse = getActionResponse(portletMvcResult);
                for (String name : names) {
                    MatcherAssert.assertThat("Unexpected render parameter '" + name + "'",
                            mockActionResponse.getRenderParameterMap().keySet(),
                            Matchers.not(Matchers.hasItem(name)));
                }
            }
        };
    }

    public PortletResultMatcher count(final int count) {
        return new PortletResultMatcher() {
            public void match(PortletMvcResult result) throws Exception {
                Map<String, String[]> domainParameters = new HashMap<String, String[]>(
                        getActionResponse(result).getRenderParameterMap());
                domainParameters
                        .remove(AnnotationMethodHandlerAdapter.IMPLICIT_MODEL_RENDER_PARAMETER);
                assertEquals("Render parameter count", count, domainParameters.size());
            }
        };
    }

	private MockActionResponse getActionResponse(PortletMvcResult result) {
        assertTrue(
                "The portlet response contained in the result is not instance of MockActionResponse",
                result.getResponse() instanceof MockActionResponse);
	    return ((MockActionResponse) result.getResponse());
	}
}
