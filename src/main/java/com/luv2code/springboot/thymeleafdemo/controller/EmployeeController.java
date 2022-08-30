package com.luv2code.springboot.thymeleafdemo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;



@Controller
@RequestMapping("/employees")
public class EmployeeController {

	// load employee data
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	// add mapping for "/list"
	@GetMapping("/list")
	public String listEmployees(Model model)
	{
		List<Employee> employees = employeeService.findAll();
		// add to the spring model
		model.addAttribute("employees", employees);
		
		return "employees/list-employees";
	}
	
	// add mapping "/showFormForAdd" to add employee
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model)
	{
		// create model attribute tobind form data
		Employee theEmployee = new Employee();
		
		model.addAttribute("employee", theEmployee);
		
		return "employees/employee-form";
		
	}
	
	// add mapping "/save" tp save the employee
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee)
	{
		
		// save the employee
		employeeService.save(theEmployee);
		
		// use a redirect to prevent duplicate submission (if user refreshes the page )
		return "redirect:/employees/list";
	}
	
	// add mapping for update
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeid") int theId, Model model)
	{
		// get the Employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", theEmployee);
		
		// send over to our form
		return "employees/employee-form";
	}
	
	// add mapping to delete
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeid") int theId)
	{
		// delete the employee
		employeeService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list";
	}
}
