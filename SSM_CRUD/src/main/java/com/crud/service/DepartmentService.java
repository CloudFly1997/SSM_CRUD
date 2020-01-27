package com.crud.service;

import com.crud.bean.Department;
import com.crud.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;

	public List<Department> getDepts() {
		List<Department> departments = departmentMapper.selectByExample(null);
		return departments;
	}
}
