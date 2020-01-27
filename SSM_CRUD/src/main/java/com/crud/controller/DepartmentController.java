package com.crud.controller;

import com.crud.bean.Department;
import com.crud.bean.Message;
import com.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/depts")
	@ResponseBody
	public Message getDepts(){
		List<Department> departments = departmentService.getDepts();
		return Message.success().add("depts",departments);
	}
}
