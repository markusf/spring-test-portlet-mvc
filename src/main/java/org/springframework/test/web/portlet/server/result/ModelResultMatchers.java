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

import java.util.Map;

import javax.portlet.PortletSession;
import javax.portlet.StateAwareResponse;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.test.web.portlet.server.PortletResultMatcher;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.portlet.DispatcherPortlet;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ModelResultMatchers {


	protected ModelResultMatchers() {
	}

	public <T> PortletResultMatcher attribute(final String name, final Matcher<T> matcher) {
		return new PortletResultMatcher() {
			@SuppressWarnings("unchecked")
            public void match(PortletMvcResult result) throws Exception {
                Map<String, Object> model = getModel(result);
                MatcherAssert.assertThat("Model attribute '" + name + "'", (T) model.get(name),
                        matcher);
            }
		};
	}

	public PortletResultMatcher attribute(String name, Object value) {
		return attribute(name, Matchers.equalTo(value));
	}

    public PortletResultMatcher attributeDoesNotExist(final String... names) {
        return new PortletResultMatcher() {
            public void match(PortletMvcResult portletMvcResult) throws Exception {
                Map<String, Object> model = getModel(portletMvcResult);
                for (String name : names) {
                    assertTrue("Did not expect the attribute: " + name, null == model.get(name));
                }
            }
        };
    }

	public PortletResultMatcher attributeExists(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
				for (String name : names) {
					attribute(name, Matchers.notNullValue()).match(result);
				}
			}
		};
	}

	public PortletResultMatcher attributeHasErrors(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult portletMvcResult) throws Exception {
			    Map<String, Object> model = getModel(portletMvcResult);
				for (String name : names) {
					BindingResult result = BindingResultUtils.getRequiredBindingResult(model, name);
					assertTrue("No errors for attribute: " + name, result.hasErrors());
				}
			}
		};
	}

	public PortletResultMatcher attributeHasNoErrors(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult PortletMvcResult) throws Exception {
			    Map<String, Object> model = getModel(PortletMvcResult);
				for (String name : names) {
					BindingResult result = BindingResultUtils.getRequiredBindingResult(model, name);
					assertTrue("No errors for attribute: " + name, !result.hasErrors());
				}
			}
		};
	}

	public PortletResultMatcher attributeHasFieldErrors(final String name, final String... fieldNames) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult PortletMvcResult) throws Exception {
			    Map<String, Object> model = getModel(PortletMvcResult);
				BindingResult result = BindingResultUtils.getRequiredBindingResult(model, name);
				assertTrue("No errors for attribute: '" + name + "'", result.hasErrors());
				for (final String fieldName : fieldNames) {
					assertTrue("No errors for field: '" + fieldName + "' of attribute: " + name,
							result.hasFieldErrors(fieldName));
				}
			}
		};
	}

	public <T> PortletResultMatcher hasNoErrors() {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
			    Map<String, Object> model = getModel(result);
				for (Object value : model.values()) {
					if (value instanceof BindingResult) {
						assertTrue("Unexpected binding error(s): " + value, !((BindingResult) value).hasErrors());
					}
				}
			}
		};
	}

	public <T> PortletResultMatcher size(final int size) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
			    Map<String, Object> model = getModel(result);
				int actual = 0;
				for (String key : model.keySet()) {
					if (!key.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
						actual++;
					}
				}
				assertEquals("Model size", size, actual);
			}
		};
	}

    private Map<String, Object> getModel(PortletMvcResult portletMvcResult) {
        ModelAndView mav = portletMvcResult.getModelAndView();
        Map<String, Object> model = null;
        if (mav != null) {
            model = mav.getModel();
        } else {
            if (portletMvcResult.getResponse() instanceof StateAwareResponse) {
                StateAwareResponse response = (StateAwareResponse) portletMvcResult.getResponse();
                // check if there are action exceptions
                assertTrue(
                        "No actionExceptions expected, but got: "
                                + StringUtils.join(response.getRenderParameterMap().get(
                                        DispatcherPortlet.ACTION_EXCEPTION_RENDER_PARAMETER)),
                        !response.getRenderParameterMap().containsKey(
                                DispatcherPortlet.ACTION_EXCEPTION_RENDER_PARAMETER));
                // look for implicit model in session
                PortletSession session = portletMvcResult.getRequest().getPortletSession(false);
                if (session != null) {
                    ExtendedModelMap implicitModel = (ExtendedModelMap) session
                            .getAttribute(AnnotationMethodHandlerAdapter.IMPLICIT_MODEL_SESSION_ATTRIBUTE);
                    if (implicitModel != null) {
                        model = implicitModel.asMap();
                    }
                }
            }
        }
        assertTrue("No Model found", model != null);
        return model;
    }
}