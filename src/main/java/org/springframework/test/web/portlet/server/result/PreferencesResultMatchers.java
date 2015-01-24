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

import javax.portlet.PortletPreferences;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.test.web.portlet.server.PortletResultMatcher;

public class PreferencesResultMatchers {


	protected PreferencesResultMatchers() {
	}

	public PortletResultMatcher has(final String name, final Matcher<String> matcher) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
                MatcherAssert.assertThat("Preference '" + name + "'",
                        getPortletPreferences(result).getValue(name, null), matcher);
			}
		};
	}

	public PortletResultMatcher has(String name, String value) {
		return has(name, Matchers.equalTo(value));
	}

	public PortletResultMatcher exists(final String... names) {
		return new PortletResultMatcher() {
			public void match(PortletMvcResult result) throws Exception {
				PortletPreferences preferences = getPortletPreferences(result);
				for (String name : names) {
				    MatcherAssert.assertThat("Preference '" + name + "'",
				            preferences.getMap().keySet(),
				            Matchers.hasItem(name));
				}
			}
		};
	}

    public PortletResultMatcher count(final int count) {
        return new PortletResultMatcher() {
            public void match(PortletMvcResult result) throws Exception {
                assertEquals("Preference count", count, getPortletPreferences(result).getMap().size());
            }
        };
    }

	private PortletPreferences getPortletPreferences(PortletMvcResult result) {
	    return result.getRequest().getPreferences();
	}

}
