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

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.test.web.portlet.server.DefaultPortletMvcResult;
import org.springframework.test.web.portlet.server.PortletMvcResult;

/**
 * @author Andrius Kurtinaitis
 */
public class RenderParametersResultMatchersTests {

	private RenderParametersResultMatchers matchers;

	private PortletMvcResult mvcResult;

	@Before
	public void setUp() throws Exception {
		this.matchers = new RenderParametersResultMatchers();
		MockActionResponse response = new MockActionResponse();
		response.setRenderParameter("key", "value");
		this.mvcResult = new DefaultPortletMvcResult(new MockActionRequest(), response);
	}

	@Test
	public void has() throws Exception {
		this.matchers.has("key", "value").match(this.mvcResult);
	}

	@Test(expected=AssertionError.class)
	public void has_wrongValue() throws Exception {
		this.matchers.has("key", "").match(this.mvcResult);
	}

    @Test(expected=AssertionError.class)
    public void has_null() throws Exception {
        this.matchers.has("key", (String)null).match(this.mvcResult);
    }

    @Test
    public void hasMatcher() throws Exception {
        this.matchers.has("key", is("value")).match(this.mvcResult);
    }

    @Test(expected=AssertionError.class)
    public void hasMatcher_wrongValue() throws Exception {
        this.matchers.has("key", is("")).match(this.mvcResult);
    }

    @Test
    public void exists() throws Exception {
        this.matchers.exists("key").match(this.mvcResult);
    }

    @Test(expected=AssertionError.class)
    public void exists_wrongValue() throws Exception {
        this.matchers.exists("").match(this.mvcResult);
    }

    @Test
    public void count() throws Exception {
        this.matchers.count(1).match(this.mvcResult);
    }

    @Test(expected=AssertionError.class)
    public void count_wrongValue() throws Exception {
        this.matchers.count(2).match(this.mvcResult);
    }
}
