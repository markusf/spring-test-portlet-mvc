package org.springframework.test.web.portlet.webapp;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.test.web.Person;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
