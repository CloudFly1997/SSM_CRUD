package com.crud.controller;

import com.crud.bean.Employee;
import com.crud.bean.Message;
import com.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	/**
	 * 返回员工信息（分页）
	 *
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Message getEmpsWithJson(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
		//PageHelper分页
		//根据emp_id 升序排序
		String orderBy = "emp_id asc";
		PageHelper.startPage(pageNumber, 5, orderBy);
		List<Employee> employees = employeeService.getAll();
		//分页信息+员工信息
		PageInfo pageInfo = new PageInfo(employees, 5);

		return Message.success().add("pageInfo", pageInfo);
	}

	/**
	 * 保存员工
	 *
	 * @return
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Message saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Message.success();
		}
	}

	/**
	 * 检验用户名是否可用
	 *
	 * @param empName
	 * @return
	 */
	@RequestMapping("/checkuser")
	@ResponseBody
	public Message checkUser(String empName) {
		//用户名是否为合法表达式
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Message.fail().add("val_msg", "用户名必须是2-5位中文或者6-16位英文和数字的组合");
		}

		//数据库用户名重复校验
		boolean flag = employeeService.checkUser(empName);
		if (flag) {
			return Message.success();
		} else {
			return Message.fail().add("val_msg", "用户名不可用");
		}
	}

	/**
	 * 根据员工id查询员工
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Message getEmp(@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Message.success().add("employee", employee);
	}

	/**
	 * 更新员工
	 *
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	@ResponseBody
	public Message saveEmp(Employee employee) {
		Logger logger = LoggerFactory.getLogger(EmployeeController.class);
		logger.debug(employee.getEmpId() + "");
		employeeService.updateEmp(employee);
		return Message.success();
	}

	/**
	 * 删除员工
	 * 批量删除：1-2-3
	 * 单个删除：1
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message deleteEmp(@PathVariable("ids") String ids) {
		if (ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");

			for (String id : str_ids) {
				del_ids.add(Integer.parseInt(id));
			}

			employeeService.deleteBatch(del_ids);
		} else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Message.success();
	}
}
