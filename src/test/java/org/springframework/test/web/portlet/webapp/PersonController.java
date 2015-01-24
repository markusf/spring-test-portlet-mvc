package org.springframework.test.web.portlet.webapp;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.test.web.Person;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class PersonController {

	@RenderMapping
	public String view(RenderRequest request, RenderResponse response, Model model) {
		Person person = new Person();
		model.addAttribute(person);
		return "viewPerson";
	}

	@ActionMapping(params="action=createNew")
    public void createNew(ActionRequest actionRequest, ActionResponse actionResponse, Model model) {
        Person person = new Person();
        model.addAttribute(person);
	    actionResponse.setRenderParameter("view", "editPersonForm");
    }
}