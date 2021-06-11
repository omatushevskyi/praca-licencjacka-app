package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.apiclub.captcha.Captcha;
import in.nareshit.raghu.model.Employee;
import in.nareshit.raghu.service.IEmployeeService;
import in.nareshit.raghu.simplecaptcha.SimpleCaptchaUtils;
import in.nareshit.raghu.validate.CaptchaValidator;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private IEmployeeService service;
	@Autowired
	private CaptchaValidator validator;
	
	
	//Simple Capthca
	private void setupSimpleCaptcha(Employee e) {
		Captcha captcha = SimpleCaptchaUtils.createSimpleCaptcha(300, 80);
		e.setHidden(captcha.getAnswer());
		e.setCaptcha("");
		e.setImage(SimpleCaptchaUtils.encodeBase64(captcha));
	}
	
	//1. Show Register Page
	@GetMapping("/register")
	public String showReg(Model model) {
		Employee e = new Employee();
		setupSimpleCaptcha(e);
		model.addAttribute("employee", e);
		return "EmpReg";
	}
	
	//2. Save data on submit
	@PostMapping("/save")
	public String save(
			@ModelAttribute("employee") Employee employee,
			@RequestParam("g-recaptcha-response")String captcha,
			Model model) 
	{
		//reCaptcha & SimpleCaptcha
		if(validator.isValid(captcha) & employee.getCaptcha().equals(employee.getHidden())) 
		{
			Integer id = service.createEmployee(employee);
			//model.addAttribute("message", "Urzutkownik nr '"+id+"' został dodany");
			model.addAttribute("employee", new Employee());
			return "redirect:all";
		} else {
			model.addAttribute("message", "Zaznacz wszystkie CAPTCHA poprawnie");
			setupSimpleCaptcha(employee);
			model.addAttribute("employee", employee);
		}
		return "EmpReg";
	}
	
	//3. Display all records
	@GetMapping("/all")
	public String fetchAll(Model model) {
		List<Employee> list = service.getAllEmployees();
		model.addAttribute("list", list);
		return "EmpData"; 
	}

}
