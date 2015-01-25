spring-test-portlet-mvc
=======================
This project is based on the concepts of the server-side component of spring-test-mvc aka MockMvc (https://github.com/spring-projects/spring-test-mvc). It tries to adapt the features for the portlet environment.

The project is still work in progress, but the general API (since derived from spring-test-mvc) is almost finished.

Feel free to contribute and send me a pull request or drop a message.

**Update:**
Thanks to the contributions by @kurtinaitis a new version has been released including support for:

* ActionRequests
* WindowState
* PortletMode
* Portlet Preferences
* Session Attributes.

##Maven Integration
The project can be obtained trough the maven central repository.
```
<dependency>
  <groupId>com.github.markusf</groupId>
  <artifactId>spring-test-portlet-mvc</artifactId>
  <version>0.1.1</version>
  <scope>test</scope>
</dependency>
```

##Sample (Standalone Application Context)

see https://github.com/markusf/spring-test-portlet-mvc/blob/master/src/test/java/org/springframework/test/web/portlet/server/samples/StandaloneAppContextTest.java

##Sample (Existing Application Context)

###Controller
```java
@Controller
@RequestMapping("VIEW")
public class PersonController {

	@RequestMapping
	public String view(RenderRequest request, RenderResponse response, Model model) {
		Person person = new Person();
		model.addAttribute(person);
		return "view";
	}

}
```

###Test
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml"})
public class BasicTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testBasicPortlet() throws Exception {
		existingApplicationContext(applicationContext).build()
			.perform(render().param("test", "test"))
			.andExpect(view().name("view"))
			.andExpect(model().attributeExists("person"))
			.andReturn();
	}

}
```
