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

import static org.springframework.test.web.AssertionErrors.assertEquals;
import static org.springframework.test.web.AssertionErrors.assertTrue;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.test.web.portlet.server.PortletResultMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.web.portlet.ModelAndView;

/**
 * Factory for assertions on the model. An instance of this class is
 * typically accessed via {@link MockMvcPortletResultMatchers#model()}.
 *
 * @author Rossen Stoyanchev
 * @author Markus Feindler
 */
public class ModelResultMatchers {


	/**
	 * Protected constructor.
	 * Use {@link MockMvcPortletResultMatchers#model()}.
	 */
	protected ModelResultMatchers() {
	}
	
	/**
	 * Assert a model attribute value with the given Hamcrest {@link Matcher}.
	 */
	public <T> PortletResultMatcher attribute(final String name, final Matcher<T> matcher) {
		return new PortletResultMatcher() {
			@SuppressWarnings("unchecked")
			public void match(PortletMvcResult result) throws Exception {
				ModelAndView mav = result.getModelAndView();
				assertTrue("No ModelAndView found", mav != null);
				MatcherAssert.assertThat("Model attribute '" + name + "'", (T) mav.getModel().get(name), matcher);
			}
		};
	}

	/**
	 * Assert a model attribute value.
	 */
	public PortletResultMatcher attribute(String name, Object value) {
		return attribute(name, Matchers.equalTo(value));
	}

	/**
	 * Assert the given model attributes exist.
	 */
	public PortletResultMatcher attributeExists(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
				assertTrue("No ModelAndView found", result.getModelAndView() != null);
				for (String name : names) {
					attribute(name, Matchers.notNullValue()).match(result);
				}
			}
		};
	}

	/**
	 * Assert the given model attribute(s) have errors.
	 */
	public PortletResultMatcher attributeHasErrors(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult PortletMvcResult) throws Exception {
				ModelAndView mav = getModelAndView(PortletMvcResult);
				for (String name : names) {
					BindingResult result = getBindingResult(mav, name);
					assertTrue("No errors for attribute: " + name, result.hasErrors());
				}
			}
		};
	}

	/**
	 * Assert the given model attribute(s) do not have errors.
	 */
	public PortletResultMatcher attributeHasNoErrors(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult PortletMvcResult) throws Exception {
				ModelAndView mav = getModelAndView(PortletMvcResult);
				for (String name : names) {
					BindingResult result = getBindingResult(mav, name);
					assertTrue("No errors for attribute: " + name, !result.hasErrors());
				}
			}
		};
	}

	/**
	 * Assert the given model attribute field(s) have errors.
	 */
	public PortletResultMatcher attributeHasFieldErrors(final String name, final String... fieldNames) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult PortletMvcResult) throws Exception {
				ModelAndView mav = getModelAndView(PortletMvcResult);
				BindingResult result = getBindingResult(mav, name);
				assertTrue("No errors for attribute: '" + name + "'", result.hasErrors());
				for (final String fieldName : fieldNames) {
					assertTrue("No errors for field: '" + fieldName + "' of attribute: " + name,
							result.hasFieldErrors(fieldName));
				}
			}
		};
	}

	/**
	 * Assert the model has no errors.
	 */
	public <T> PortletResultMatcher hasNoErrors() {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
				ModelAndView mav = getModelAndView(result);
				for (Object value : mav.getModel().values()) {
					if (value instanceof BindingResult) {
						assertTrue("Unexpected binding error(s): " + value, !((BindingResult) value).hasErrors());
					}
				}
			}
		};
	}

	/**
	 * Assert the number of model attributes.
	 */
	public <T> PortletResultMatcher size(final int size) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
				ModelAndView mav = getModelAndView(result);
				int actual = 0;
				for (String key : mav.getModel().keySet()) {
					if (!key.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
						actual++;
					}
				}
				assertEquals("Model size", size, actual);
			}
		};
	}

	private ModelAndView getModelAndView(PortletMvcResult PortletMvcResult) {
		ModelAndView mav = PortletMvcResult.getModelAndView();
		assertTrue("No ModelAndView found", mav != null);
		return mav;
	}

	private BindingResult getBindingResult(ModelAndView mav, String name) {
		BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + name);
		assertTrue("No BindingResult for attribute: " + name, result != null);
		return result;
	}

}
