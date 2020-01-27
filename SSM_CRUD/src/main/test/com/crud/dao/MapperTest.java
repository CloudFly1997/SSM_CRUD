package com.crud.dao;

import com.crud.BaseTest;
import com.crud.bean.Employee;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class MapperTest extends BaseTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;

	@Test
	public void testInsertSelective() {

//		departmentMapper.insertSelective(new Department(null, "开发部"));
//		departmentMapper.insertSelective(new Department(null, "测试部"));
//		Logger logger = LoggerFactory.getLogger(MapperTest.class);
//		logger.debug("插入 Employee");
//		employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@qq.com", 1));
//		logger.debug("插入 Employee 成功");

		//插入一千条数据
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 500; i++) {
			String uuid1 = UUID.randomUUID().toString().substring(0, 5) + i;
			String uuid2 = UUID.randomUUID().toString().substring(0, 5) + i;
			mapper.insertSelective(new Employee(null, uuid1, "M", uuid1 + "@qq.com", 1));
			mapper.insertSelective(new Employee(null, uuid2, "W", uuid2 + "@qq.com", 2));
		}
	}
}