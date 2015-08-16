package tr.org.lkd.lyk2015.camp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tr.org.lkd.lyk2015.camp.model.Admin;
import tr.org.lkd.lyk2015.camp.model.Instructor;


@Controller
@RequestMapping("/admins")
public class AdminController {
	
	/*@RequestMapping(value = " ", method = RequestMethod.POST)
	public String createForm(@ModelAttribute Admin admin) {
		
		return "createForm";
	}*/

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Admin admin) {
		
		return "adminCreateForm";
	}
}
