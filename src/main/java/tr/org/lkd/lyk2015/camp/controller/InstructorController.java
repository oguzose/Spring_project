package tr.org.lkd.lyk2015.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tr.org.lkd.lyk2015.camp.model.Instructor;

@Controller
@RequestMapping("/instructors")
public class InstructorController {
	
	/*@RequestMapping(value = " ", method = RequestMethod.POST)
	public String createForm(@ModelAttribute Instructor instructor) {
		
		return "createForm";
	}
	*/

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Instructor instructor) {
		
		return "instructorCreateForm";
	}
	

	

}
