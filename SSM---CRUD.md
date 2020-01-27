## 功能点

- 分页
- 数据校验
  - JQuery 前端校验
  - JSR303 后端校验
- Ajax
- Restful 风格URI

## 技术点

- SSM（SpringMVC + Spring + MyBatis）
- MySQL
- Bootstrap
- Maven
- pagehelper
- MyBatis Generator
- Logback
- 数据源 Druid



## 环境搭建

![环境搭建](https://github.com/CloudFly1997/SSM_CRUD/blob/master/SSM---CRUD.assets/1.jpg)

### pom.xml

```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <maven.compiler.source>1.8</maven.compiler.source>
  <maven.compiler.target>1.8</maven.compiler.target>

  <spring.version>5.0.2.RELEASE</spring.version>
  <mybatis.version>3.4.5</mybatis.version>
  <alibaba.druid>1.1.20</alibaba.druid>
  <mysql.version>5.1.6</mysql.version>
</properties>

<dependencies>
  <!--springMVC-->
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>${spring.version}</version>
  </dependency>
  <!--spring-Jdbc-->
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>${spring.version}</version>
  </dependency>
  <!--spring面向切面编程-->
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>${spring.version}</version>
  </dependency>

  <!--MyBatis-->
  <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>${mybatis.version}</version>
  </dependency>
  <!--mybatis_spring整合-->
  <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.0</version>
  </dependency>

  <!--阿里 druid 数据源-->
  <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>${alibaba.druid}</version>
  </dependency>

  <!--mysql驱动包-->
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.version}</version>
  </dependency>

  <!--junit-->
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>compile</scope>
  </dependency>

  <!--servlet和jsp-->
  <dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.0.1</version>
    <scope>provided</scope>
  </dependency>
  <dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.0</version>
    <scope>provided</scope>
  </dependency>

  <!--jstl-->
  <dependency>
    <groupId>jstl</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
  </dependency>
</dependencies>
```

### BootStrap

- index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <title></title>
        <!-- 引入 Bootstrap 样式 -->
        <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    </head>
    <body>
        <h2>Hello World!</h2>
        <button class="btn btn-default">按钮</button>


        <!-- 引入Jquery -->
        <script type="text/javascript" src="static/js/jquery.min.js"/>
        <!-- 引入 Bootstrap 样式 -->
        <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>
    </body>
</html>
```



## SSM 配置文件

![项目](https://github.com/CloudFly1997/SSM_CRUD/blob/master/SSM---CRUD.assets/2.jpg)

### web.xml

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <!--Spring的配置文件-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

  <!--SpringMVC前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--加载springmvc.xml配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--启动服务器，创建该servlet-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

  <!--解决中文乱码的过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceRequestEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
    <init-param>
      <param-name>forceResponseEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!--RestFul-->
  <filter>
    <filter-name>hiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>hiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <filter>
    <filter-name>HttpPutFormContentFilter</filter-name>
    <filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>HttpPutFormContentFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
  </welcome-file-list>
</web-app>
```

### springmvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--注解扫描-->
    <context:component-scan base-package="com.crud" use-default-filters="false">
        <!--只扫描Controller注解-->
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"></property>
        <property name="suffix" value=".html"></property>
    </bean>

    <!--springmvc不能处理的请求交给tomcat-->
    <mvc:default-servlet-handler/>
    <!--开启SpringMVC注解支持-->
    <mvc:annotation-driven/>
</beans>
```

### applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd
           http://www.springframework.org/schema/aop
           http://www.springframework.org/schema/aop/spring-aop.xsd
           http://www.springframework.org/schema/tx
           http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--注解扫描-->
    <context:component-scan base-package="com.crud">
        <!--不扫描Controller注解-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--引入数据库信息-->
    <<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location" value="classpath:jdbc.properties"/>
    </bean>

    <!--数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${driverClassName}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
    </bean>

    <!--SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--指定MyBatis全局配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--指定MyBatis的mapper文件路径-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

    <!--配置dao层所在包-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.crud.dao"/>
    </bean>

    <!--事务控制-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--管理数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!--开启注解事务控制-->
    <aop:config>
        <!--切入点表达式-->
        <aop:pointcut id="txPoint" expression="execution(* com.curd.service..*(..))"/>
        <!--配置事务增强-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPoint"/>
    </aop:config>

    <!--事务增强：事务如何切入-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!--拦截所有方法-->
            <tx:method name="*"/>
            <!--拦截get*()方法-->
            <tx:method name="get*" read-only="true"/>
        </tx:attributes>
    </tx:advice>
</beans>
```

### mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!-- 驼峰命名 -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```

### jdbc.properties

```properties
url=jdbc:mysql://192.168.1.100:3306/ssm_crud?useUnicode=true&characterEncoding=utf8
driverClassName=com.mysql.jdbc.Driver
username=root
password=root
```



## 数据库

```mysql
create database ssm_crud;
use ssm_crud;
create table tbl_department (
    dept_id int(11) not null primary key auto_increment,
    dept_name varchar(255) not null
)engine=InnoDB auto_increment=1 default charset=utf8;
create table tbl_employee (
    emp_id int(11) not null primary key auto_increment,
    emp_name varchar(255) not null,
    gender char(1),
    email varchar(255),
    d_id int(11),
    constraint fk_employee foreign key (d_id) references tbl_department(dept_id)
)engine=InnoDB auto_increment=1 default charset=utf8;
```



## MyBatis 逆向工程

- pom.xml

  ```xml
  <!-- mybatis代码生成插件 -->
  <plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.7</version>
    <configuration>
      <!--配置文件的位置-->
      <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
      <verbose>true</verbose>
      <overwrite>true</overwrite>
    </configuration>
    <executions>
      <execution>
        <id>Generate MyBatis Artifacts</id>
        <goals>
          <goal>generate</goal>
        </goals>
      </execution>
    </executions>
    <dependencies>
      <dependency>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-core</artifactId>
        <version>1.3.7</version>
      </dependency>
    </dependencies>
  </plugin>
  ```

- generatorConfig.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE generatorConfiguration
          PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
          "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
  
  <generatorConfiguration>
      <!--mysql 连接数据库jar 这里选择自己本地位置;
        如果不知道maven本地仓库地址，可以使用EveryThing工具全局搜索mysql-connector-java，找到jar包位置；
        也可以手动下载一个jar放在指定位置，进行引用。
        -->
      <classPathEntry location="C:/Maven/repository/mysql/mysql-connector-java/5.0.8/mysql-connector-java-5.0.8.jar"/>
  
      <context id="testTables" targetRuntime="MyBatis3">
          <commentGenerator>
              <!-- 是否去除自动生成的注释,true：是,false:否 -->
              <property name="suppressAllComments" value="true"/>
          </commentGenerator>
  
          <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
          <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                          connectionURL="jdbc:mysql://192.168.1.100:3306/ssm_crud" userId="root"
                          password="root">
          </jdbcConnection>
  
          <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
                 NUMERIC 类型解析为java.math.BigDecimal -->
          <javaTypeResolver>
              <property name="forceBigDecimals" value="false"/>
          </javaTypeResolver>
  
          <!-- 指定javaBean生成的位置
                  targetPackage：生成的类要放的包，真实的包受enableSubPackages属性控制；
                  targetProject：目标项目，指定一个存在的目录下，生成的内容会放到指定目录中，如果目录不存在，MBG不会自动建目录
               -->
          <javaModelGenerator targetPackage="com.crud.bean" targetProject="src/main/java">
              <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false；如果多个数据库改为true分目录 -->
              <property name="enableSubPackages" value="false"/>
              <!-- 设置是否在getter方法中，对String类型字段调用trim()方法 -->
              <property name="trimStrings" value="true"/>
          </javaModelGenerator>
  
          <!--  指定mapper映射文件生成的位置
                 targetPackage、targetProject同javaModelGenerator中作用一样-->
          <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources">
              <property name="enableSubPackages" value="false"/>
          </sqlMapGenerator>
  
          <!-- 指定mapper接口生成的位置
               targetPackage、targetProject同javaModelGenerator中作用一样
               -->
          <javaClientGenerator type="XMLMAPPER" targetPackage="com.crud.dao" targetProject="src/main/java">
              <property name="enableSubPackages" value="false"/>
          </javaClientGenerator>
  
          <!-- 指定数据库表
              domainObjectName：生成的domain类的名字,当表名和domain类的名字有差异时一定要设置，如果不设置，直接使用表名作为domain类的名字；
              可以设置为somepck.domainName，那么会自动把domainName类再放到somepck包里面；
              -->
          <table tableName="tbl_employee" domainObjectName="Employee"></table>
          <table tableName="tbl_department" domainObjectName="Department"></table>
      </context>
  </generatorConfiguration>
  ```

- 使用

  ![MyBatis 逆向工程 使用](https://github.com/CloudFly1997/SSM_CRUD/blob/master/SSM---CRUD.assets/3.jpg)



## 修改mapper文件

### Employee.java

```java
public class Employee {
    private Integer empId;

    private String empName;

    private String gender;

    private String email;

    private Integer dId;
    //部门信息
    private Department department;

    public Employee() {
    }

    public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.dId = dId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
```

### EmployeeMapper.java

```java
public interface EmployeeMapper {
  	List<Employee> selectByExampleWithDept(EmployeeExample example);

    Employee selectByPrimaryKeyWithDept(Integer empId);
}
```

### EmployeeMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.crud.dao.EmployeeMapper">
    <resultMap id="BaseResultMapWithDept" type="com.crud.bean.Employee">
      <id column="emp_id" jdbcType="INTEGER" property="empId" />
      <result column="emp_name" jdbcType="VARCHAR" property="empName" />
      <result column="gender" jdbcType="CHAR" property="gender" />
      <result column="email" jdbcType="VARCHAR" property="email" />
      <result column="d_id" jdbcType="INTEGER" property="dId" />
      <association property="department" javaType="com.crud.bean.Department">
        <id column="dept_id" property="deptId"></id>
        <result column="dept_name" property="deptName"></result>
      </association>
    </resultMap>
  	<sql id="BaseWithDept_Column_list">
   	 	e.emp_id, e.emp_name, e.gender, e.email, e.d_id, d.dept_id, d.dept_name
  	</sql>
  	<select id="selectByExampleWithDept" parameterType="com.crud.bean.EmployeeExample" resultMap="BaseResultMapWithDept">
      select
      <if test="distinct">
        distinct
      </if>
      <include refid="BaseWithDept_Column_list" />
      from tbl_employee e
      left join tbl_department d on e.d_id = d.dept_id
      <if test="_parameter != null">
        <include refid="Example_Where_Clause" />
      </if>
      <if test="orderByClause != null">
        order by ${orderByClause}
      </if>
    </select>
    <select id="selectByPrimaryKeyWithDept" parameterType="java.lang.Integer" resultMap="BaseResultMapWithDept">
      select
      <include refid="BaseWithDept_Column_list" />
      from tbl_employee e
      left join tbl_department d on e.d_id = d.dept_id
      where emp_id = #{empId,jdbcType=INTEGER}
    </select>
</mapper>
```



## Spring 单元测试

### pom.xml

```xml
<!--spring测试-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>${spring.version}</version>
</dependency>
```

### BaseTest.java

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BaseTest {
	
}
```

### MapperTest.java

```java
public class MapperTest extends BaseTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;

	@Test
	public void testInsertSelective(){
		departmentMapper.insertSelective(new Department(null,"开发部"));
		departmentMapper.insertSelective(new Department(null,"测试部"));

		employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@qq.com",1));
	}
}
```



## 批量操作

### applicationContext.xml

```xml
<!--批量操作的sqlSession-->
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
  <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"/>
  <constructor-arg name="executorType" value="BATCH"/>
</bean>
```

### MapperTest.java

```java
public class MapperTest extends BaseTest {
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
  
	@Test
	public void testInsertSelective(){
    	EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
      for (int i = 0; i < 500; i++) {
        String uuid1 = UUID.randomUUID().toString().substring(0, 5) + i;
        String uuid2 = UUID.randomUUID().toString().substring(0, 5) + i;
        mapper.insertSelective(new Employee(null, uuid, "M", uuid + "@qq.com", 1));
        mapper.insertSelective(new Employee(null, uuid, "W", uuid + "@qq.com", 2));
      }
	}
}
```



## Logback 日志配置

### pom.xml

```xml
<!-- Logback 日志 -->
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.3</version>
</dependency>
```

### Logback.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <!-- 日志级别 -->
    <!-- TRACE < DEBUG < INFO < WARN < ERROR -->
    <property name="log.level" value="debug"/>

    <!-- 日志文件保留时间 -->
    <property name="log.maxHistory" value="30"/>

    <!-- 日志文件保存路径：catalina.base Tomcat根目录 -->
    <property name="log.filePath" value="${catalina.base}/logs/"/>

    <!-- 日志输出格式 -->
    <property name="log.pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %msg%n"/>

    <!-- 控制台设置 -->
    <appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${log.pattern}</pattern>
        </encoder>
    </appender>

    <!-- DEBUG -->
    <appender name="debugAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 文件路径 -->
        <file>${log.filePath}/debug.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 文件名称 -->
            <fileNamePattern>${log.filePath}/debug/debug.%d{yyyy-MM-dd}.log.gz</fileNamePattern>
            <!-- 日志文件最大保留数量 -->
            <MaxHitory>${log.maxHistory}</MaxHitory>
        </rollingPolicy>
        <encoder>
            <pattern>${log.pattern}</pattern>
        </encoder>
        <!-- 只记录DEBUG的日志 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- INFO -->
    <appender name="infoAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 文件路径 -->
        <file>${log.filePath}/info.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 文件名称 -->
            <fileNamePattern>${log.filePath}/info/info.%d{yyyy-MM-dd}.log.gz</fileNamePattern>
            <!-- 日志文件最大保留数量 -->
            <MaxHitory>${log.maxHistory}</MaxHitory>
        </rollingPolicy>
        <encoder>
            <pattern>${log.pattern}</pattern>
        </encoder>
        <!-- 只记录INFO的日志 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- ERROR -->
    <appender name="errorAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 文件路径 -->
        <file>${log.filePath}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 文件名称 -->
            <fileNamePattern>${log.filePath}/error/error.%d{yyyy-MM-dd}.log.gz</fileNamePattern>
            <!-- 日志文件最大保留数量 -->
            <MaxHitory>${log.maxHistory}</MaxHitory>
        </rollingPolicy>
        <encoder>
            <pattern>${log.pattern}</pattern>
        </encoder>
        <!-- 只记录ERROR的日志 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <logger name="com.crud" level="${log.level}" additivity="true">
        <appender-ref ref="debugAppender"/>
        <appender-ref ref="infoAppender"/>
        <appender-ref ref="errorAppender"/>
    </logger>

    <root level="info">
        <appender-ref ref="consoleAppender"/>
    </root>
</configuration>
```

### MapperTest.java（使用）

```java
public class MapperTest extends BaseTest {
	@Autowired
	EmployeeMapper employeeMapper;

	@Test
	public void testInsertSelective() {
		Logger logger = LoggerFactory.getLogger(MapperTest.class);
		logger.debug("插入 Employee");
		employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@qq.com", 1));
		logger.debug("插入 Employee 成功");
	}
}
```



## PageHelper

### pom.xml

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.1.2</version>
</dependency>
```

### mybatis-config.xml

```xml
<plugins>
  <plugin interceptor="com.github.pagehelper.PageInterceptor">
    	<!-- 分页合理化 -->
      <property name="reasonable" value="true"/>
  </plugin>
</plugins>
```



## 分页查询员工信息

### Message.java（Json数据实体类）

```java
public class Message {

	//状态码 100：成功 200：失败
	private int code;
	//提示信息
	private String message;

	//返回给浏览器数据
	private Map<String, Object> json = new HashMap<>();

	public static Message success() {
		Message result = new Message();
		result.setCode(100);
		result.setMessage("处理成功！");
		return result;
	}

	public static Message fail() {
		Message result = new Message();
		result.setCode(200);
		result.setMessage("处理失败！");
		return result;
	}

	public Message add(String key, Object value) {
		this.getJson().put(key, value);
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}
}
```

### index.html（前端）

```jsp
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath(){
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }
        
        /*页面加载完成，发起请求：获取分页数据*/
        $(function(){
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url:getRootPath()+"/emps",
                data:{
                    pageNumber:pageNumer
                },
                type:"GET",
                success:function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }
        
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees,function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M"?"男":"女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var delBtn =$("<button></button>").addClass("btn btn-danger btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };
        
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 "+result.json.pageInfo.pageNum+" 页，总 "+result.json.pageInfo.pages+" 页，总 "+result.json.pageInfo.total+" 条记录");
        };
        
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href","#").append($("<span></span>").append("&laquo;")));
            if(result.json.pageInfo.hasPreviousPage == false){
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            }else{
                firstPageLi.click(function(){
                    to_page(1);
                });
                prePageLi.click(function(){
                    to_page(result.json.pageInfo.pageNum -1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums,function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if(result.json.pageInfo.pageNum == item){
                    numLi.addClass("active");
                }
                numLi.click(function () {
                   to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href","#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
            if(result.json.pageInfo.hasNextPage == false){
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            }else{
                nextPageLi.click(function(){
                    to_page(result.json.pageInfo.pageNum +1);
                });
                lastPageLi.click(function(){
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };
    </script>
</head>
<body>
<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### EmployeeController.java

```java
@Controller
public class EmployeeController{
	@Autowired
	EmployeeService employeeService;

	/**
	 * 返回员工信息（分页）
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Message getEmpsWithJson(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber){
		//PageHelper分页
		//根据emp_id 升序排序
		String orderBy = "emp_id asc";
		PageHelper.startPage(pageNumber,5,orderBy);
		List<Employee> employees = employeeService.getAll();
		//分页信息+员工信息
		PageInfo pageInfo = new PageInfo(employees,5);

		return Message.success().add("pageInfo",pageInfo);
	}
}
```

### EmployeeService.java

```java
@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 查询所有员工信息
	 * @return
	 */
	public List<Employee> getAll(){
		return employeeMapper.selectByExampleWithDept(null);
	}
} 
```



## 查询部门信息

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath(){
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        /*页面加载完成，发起请求：获取分页数据*/
        $(function(){
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url:getRootPath()+"/emps",
                data:{
                    pageNumber:pageNumer
                },
                type:"GET",
                success:function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }
        
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees,function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M"?"男":"女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var delBtn =$("<button></button>").addClass("btn btn-danger btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };
        
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 "+result.json.pageInfo.pageNum+" 页，总 "+result.json.pageInfo.pages+" 页，总 "+result.json.pageInfo.total+" 条记录");
        };
        
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href","#").append($("<span></span>").append("&laquo;")));
            if(result.json.pageInfo.hasPreviousPage == false){
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            }else{
                firstPageLi.click(function(){
                    to_page(1);
                });
                prePageLi.click(function(){
                    to_page(result.json.pageInfo.pageNum -1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums,function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if(result.json.pageInfo.pageNum == item){
                    numLi.addClass("active");
                }
                numLi.click(function () {
                   to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href","#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
            if(result.json.pageInfo.hasNextPage == false){
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            }else{
                nextPageLi.click(function(){
                    to_page(result.json.pageInfo.pageNum +1);
                });
                lastPageLi.click(function(){
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function(){
            $("#emp_add_modal_btn").click(function() {
                $("#empAddModal select").empty();
                //发送ajax请求 获取部门信息
                getDepts();

                $("#empAddModal").modal({
                    backdrop:"static"
                });
            });
        });

        function getDepts() {
            $.ajax({
                url:getRootPath()+"/depts",
                type:"GET",
                success:function (result) {
                    $.each(result.json.depts,function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value",this.deptId);
                        optionElement.appendTo("#empAddModal select");
                    });
                }
            });
        }
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input" placeholder="empName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input" placeholder="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### DepartmentController.java

```java
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
```

### DepartmentService.java

```java
@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;

	public List<Department> getDepts() {
		List<Department> departments = departmentMapper.selectByExample(null);
		return departments;
	}
}
```



## 新增员工信息

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath(){
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        //总记录数
        var totalRecord;

        /*页面加载完成，发起请求：获取分页数据*/
        $(function(){
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url:getRootPath()+"/emps",
                data:{
                    pageNumber:pageNumer
                },
                type:"GET",
                success:function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }
        
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees,function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M"?"男":"女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var delBtn =$("<button></button>").addClass("btn btn-danger btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };
        
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 "+result.json.pageInfo.pageNum+" 页，总 "+result.json.pageInfo.pages+" 页，总 "+result.json.pageInfo.total+" 条记录");

            totalRecord = result.json.pageInfo.total;
        };
        
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href","#").append($("<span></span>").append("&laquo;")));
            if(result.json.pageInfo.hasPreviousPage == false){
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            }else{
                firstPageLi.click(function(){
                    to_page(1);
                });
                prePageLi.click(function(){
                    to_page(result.json.pageInfo.pageNum -1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums,function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if(result.json.pageInfo.pageNum == item){
                    numLi.addClass("active");
                }
                numLi.click(function () {
                   to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href","#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
            if(result.json.pageInfo.hasNextPage == false){
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            }else{
                nextPageLi.click(function(){
                    to_page(result.json.pageInfo.pageNum +1);
                });
                lastPageLi.click(function(){
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function(){
            $("#emp_add_modal_btn").click(function() {
                $("#empAddModal select").empty();
                //发送ajax请求 获取部门信息
                getDepts();

                $("#empAddModal").modal({
                    backdrop:"static"
                });
            });

            $("#emp_save_btn").click(function () {
                //发送ajax请求 保存员工
                $.ajax({
                    url:getRootPath()+"/emp",
                    type:"POST",
                    data:$("#empAddModal form").serialize(),
                    success:function (result) {
                        //关闭模态框
                        $("#empAddModal").empty();
                        $("#empAddModal").modal("hide");

                        //最后一页
                        to_page(totalRecord);
                    }
                });
            });
        });

        function getDepts() {
            $.ajax({
                url:getRootPath()+"/depts",
                type:"GET",
                success:function (result) {
                    $.each(result.json.depts,function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value",this.deptId);
                        optionElement.appendTo("#empAddModal select");
                    });
                }
            });
        }
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input" placeholder="empName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input" placeholder="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### EmployeeController.java

```java
@Controller
public class EmployeeController{
	@Autowired
	EmployeeService employeeService;

	/**
	 * 返回员工信息（分页）
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Message getEmpsWithJson(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber){
		//PageHelper分页
		//根据emp_id 升序排序
		String orderBy = "emp_id asc";
		PageHelper.startPage(pageNumber,5,orderBy);
		List<Employee> employees = employeeService.getAll();
		//分页信息+员工信息
		PageInfo pageInfo = new PageInfo(employees,5);

		return Message.success().add("pageInfo",pageInfo);
	}

	/**
	 * 保存员工
	 * @return
	 */
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	@ResponseBody
	public Message saveEmp(Employee employee){
		employeeService.saveEmp(employee);
		return Message.success();
	}
}
```

### EmployeeService.java

```java
@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 查询所有员工信息
	 * @return
	 */
	public List<Employee> getAll(){
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * 保存员工
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}
}
```



## 前端校验

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath() {
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        //总记录数
        var totalRecord;

        /*页面加载完成，发起请求：获取分页数据*/
        $(function () {
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url: getRootPath() + "/emps",
                data: {
                    pageNumber: pageNumer
                },
                type: "GET",
                success: function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }

        //显示员工数据
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees, function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M" ? "男" : "女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };

        //显示分页文字
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 " + result.json.pageInfo.pageNum + " 页，总 " + result.json.pageInfo.pages + " 页，总 " + result.json.pageInfo.total + " 条记录");

            totalRecord = result.json.pageInfo.total;
        };

        //显示分页条
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&laquo;")));
            if (result.json.pageInfo.hasPreviousPage == false) {
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            } else {
                firstPageLi.click(function () {
                    to_page(1);
                });
                prePageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum - 1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if (result.json.pageInfo.pageNum == item) {
                    numLi.addClass("active");
                }
                numLi.click(function () {
                    to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));
            if (result.json.pageInfo.hasNextPage == false) {
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            } else {
                nextPageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum + 1);
                });
                lastPageLi.click(function () {
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function () {
            //新增按钮
            $("#emp_add_modal_btn").click(function () {
                $("#empAddModal select").empty();
                //清除表单数据（表单数据、表单样式）
                reset_form("#empAddModal form");

                //发送ajax请求 获取部门信息
                getDepts();

                $("#empAddModal").modal({
                    backdrop: "static"
                });
            });

            $("#empName_add_input").change(function () {
                var empName = this.value;
                //发送ajax请求  校验用户名
                $.ajax({
                    url: getRootPath() + "/checkuser",
                    data: "empName=" + empName,
                    type: "POST",
                    success: function (result) {
                        if (result.code == 100) {
                            show_validate_msg("#empName_add_input", "success", "用户名可用");
                            $("#emp_save_btn").attr("ajax-val", "success");
                        } else {
                            show_validate_msg("#empName_add_input", "error", result.json.val_msg);
                            $("#emp_save_btn").attr("ajax-val", "error");
                        }
                    }
                });
            });

            //保存按钮
            $("#emp_save_btn").click(function () {
                //校验数据
                if ($(this).attr("ajax-val") == "error") {
                    return false;
                }
                if (!validate_add_form()) {
                    return false;
                }

                //发送ajax请求 保存员工
                $.ajax({
                    url: getRootPath() + "/emp",
                    type: "POST",
                    data: $("#empAddModal form").serialize(),
                    success: function (result) {
                        //关闭模态框
                        $("#empAddModal").modal("hide");

                        //最后一页
                        to_page(totalRecord);
                    }
                });
            });
        });

        //发送ajax请求  获取部门信息
        function getDepts() {
            $.ajax({
                url: getRootPath() + "/depts",
                type: "GET",
                success: function (result) {
                    $.each(result.json.depts, function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                        optionElement.appendTo("#empAddModal select");
                    });
                }
            });
        }

        //校验数据
        function validate_add_form() {
            var empName = $("#empName_add_input").val();
            var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
            if (!regName.test(empName)) {
                show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
                return false;
            } else {
                show_validate_msg("#empName_add_input", "success", "");
            }

            var email = $("#email_add_input").val();
            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if (!regEmail.test(email)) {
                show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
                return false;
            } else {
                show_validate_msg("#email_add_input", "success", "");
            }
            return true;
        }

        function show_validate_msg(element, status, message) {
            $(element).parent().removeClass("has-success has-error");
            $(element).next("span").text("");
            if ("success" == status) {
                $(element).parent().addClass("has-success");
                $(element).next("span").text(message);

            } else if ("error" == status) {
                $(element).parent().addClass("has-error");
                $(element).next("span").text(message);
            }
        }

        function reset_form(element) {
            $(element)[0].reset();
            $(element).find("*").removeClass("has-error has-success");
            $(element).find(".help-block").text("");
        }
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### EmployeeController.java

```java
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
	public Message saveEmp(Employee employee) {
		employeeService.saveEmp(employee);
		return Message.success();
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
		if(!empName.matches(regx)){
			return Message.fail().add("val_msg","用户名必须是2-5位中文或者6-16位英文和数字的组合");
		}

		//数据库用户名重复校验
		boolean flag = employeeService.checkUser(empName);
		if (flag) {
			return Message.success();
		} else {
			return Message.fail().add("val_msg","用户名不可用");
		}
	}
}
```

### EmployeeService.java

```java
@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 查询所有员工信息
	 *
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * 保存员工
	 *
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 检验用户名是否可用
	 *
	 * @param empName
	 * @return true：用户名可用  false：用户名不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}
}
```



## JSR303校验

### pom.xml

```xml
<!-- JSR303校验 -->
<dependency>
  <groupId>org.hibernate</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>6.0.17.Final</version>
</dependency>
```

### Employee.java

```java
public class Employee {

    private Integer empId;

    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})",message = "用户名必须是2-5位中文或者6-16位英文和数字的组合")
    private String empName;

    private String gender;

    @Email(message = "邮箱格式不正确")
    private String email;

    private Integer dId;
    //部门信息
    private Department department;

    public Employee() {
    }

    public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.dId = dId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
```

### EmployeeController.java

```java
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
		if(result.hasErrors()){
			Map<String,Object> map = new HashMap<>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields",map);
		}else{
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
		if(!empName.matches(regx)){
			return Message.fail().add("val_msg","用户名必须是2-5位中文或者6-16位英文和数字的组合");
		}

		//数据库用户名重复校验
		boolean flag = employeeService.checkUser(empName);
		if (flag) {
			return Message.success();
		} else {
			return Message.fail().add("val_msg","用户名不可用");
		}
	}
}
```

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath() {
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        //总记录数
        var totalRecord;

        /*页面加载完成，发起请求：获取分页数据*/
        $(function () {
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url: getRootPath() + "/emps",
                data: {
                    pageNumber: pageNumer
                },
                type: "GET",
                success: function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }

        //显示员工数据
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees, function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M" ? "男" : "女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };

        //显示分页文字
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 " + result.json.pageInfo.pageNum + " 页，总 " + result.json.pageInfo.pages + " 页，总 " + result.json.pageInfo.total + " 条记录");

            totalRecord = result.json.pageInfo.total;
        };

        //显示分页条
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&laquo;")));
            if (result.json.pageInfo.hasPreviousPage == false) {
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            } else {
                firstPageLi.click(function () {
                    to_page(1);
                });
                prePageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum - 1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if (result.json.pageInfo.pageNum == item) {
                    numLi.addClass("active");
                }
                numLi.click(function () {
                    to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));
            if (result.json.pageInfo.hasNextPage == false) {
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            } else {
                nextPageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum + 1);
                });
                lastPageLi.click(function () {
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function () {
            //新增按钮
            $("#emp_add_modal_btn").click(function () {
                $("#empAddModal select").empty();
                //清除表单数据（表单数据、表单样式）
                reset_form("#empAddModal form");

                //发送ajax请求 获取部门信息
                getDepts();

                $("#empAddModal").modal({
                    backdrop: "static"
                });
            });

            $("#empName_add_input").change(function () {
                var empName = this.value;
                //发送ajax请求  校验用户名
                $.ajax({
                    url: getRootPath() + "/checkuser",
                    data: "empName=" + empName,
                    type: "POST",
                    success: function (result) {
                        if (result.code == 100) {
                            show_validate_msg("#empName_add_input", "success", "用户名可用");
                            $("#emp_save_btn").attr("ajax-val", "success");
                        } else {
                            show_validate_msg("#empName_add_input", "error", result.json.val_msg);
                            $("#emp_save_btn").attr("ajax-val", "error");
                        }
                    }
                });
            });

            //保存按钮
            $("#emp_save_btn").click(function () {
                //校验数据
                if (!validate_add_form()) {
                    return false;
                }
                if ($(this).attr("ajax-val") == "error") {
                    return false;
                }


                //发送ajax请求 保存员工
                $.ajax({
                    url: getRootPath() + "/emp",
                    type: "POST",
                    data: $("#empAddModal form").serialize(),
                    success: function (result) {
                        if(result.code == 100){
                            //关闭模态框
                            $("#empAddModal").modal("hide");

                            //最后一页
                            to_page(totalRecord);
                        }else{
                            //显示JSR303校验失败信息
                            if(undefined != result.json.errorFields.email){
                                show_validate_msg("#email_add_input","error",result.json.errorFields.email);
                            }
                            if(undefined != result.json.errorFields.empName){
                                show_validate_msg("#empName_add_input","error",result.json.errorFields.empName);
                            }
                        }
                    }
                });
            });
        });

        //发送ajax请求  获取部门信息
        function getDepts() {
            $.ajax({
                url: getRootPath() + "/depts",
                type: "GET",
                success: function (result) {
                    $.each(result.json.depts, function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                        optionElement.appendTo("#empAddModal select");
                    });
                }
            });
        }

        //校验数据
        function validate_add_form() {
            var empName = $("#empName_add_input").val();
            var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
            if (!regName.test(empName)) {
                show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
                return false;
            } else {
                show_validate_msg("#empName_add_input", "success", "");
            }

            var email = $("#email_add_input").val();
            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if (!regEmail.test(email)) {
                show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
                return false;
            } else {
                show_validate_msg("#email_add_input", "success", "");
            }
            return true;
        }

        function show_validate_msg(element, status, message) {
            $(element).parent().removeClass("has-success has-error");
            $(element).next("span").text("");
            if ("success" == status) {
                $(element).parent().addClass("has-success");
                $(element).next("span").text(message);

            } else if ("error" == status) {
                $(element).parent().addClass("has-error");
                $(element).next("span").text(message);
            }
        }

        function reset_form(element) {
            $(element)[0].reset();
            $(element).find("*").removeClass("has-error has-success");
            $(element).find(".help-block").text("");
        }
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```



## 修改员工信息

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath() {
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        //总记录数
        var totalRecord;
        //当前页
        var currentPage;

        /*页面加载完成，发起请求：获取分页数据*/
        $(function () {
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url: getRootPath() + "/emps",
                data: {
                    pageNumber: pageNumer
                },
                type: "GET",
                success: function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }

        //显示员工数据
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees, function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M" ? "男" : "女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                editBtn.attr("edit-id",item.empId);
                var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };

        //显示分页文字
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 " + result.json.pageInfo.pageNum + " 页，总 " + result.json.pageInfo.pages + " 页，总 " + result.json.pageInfo.total + " 条记录");

            totalRecord = result.json.pageInfo.total;
            currentPage = result.json.pageInfo.pageNum;
        };

        //显示分页条
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&laquo;")));
            if (result.json.pageInfo.hasPreviousPage == false) {
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            } else {
                firstPageLi.click(function () {
                    to_page(1);
                });
                prePageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum - 1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if (result.json.pageInfo.pageNum == item) {
                    numLi.addClass("active");
                }
                numLi.click(function () {
                    to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));
            if (result.json.pageInfo.hasNextPage == false) {
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            } else {
                nextPageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum + 1);
                });
                lastPageLi.click(function () {
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function () {
            //新增按钮
            $("#emp_add_modal_btn").click(function () {
                //清除表单数据（表单数据、表单样式）
                reset_form("#empAddModal form");

                //发送ajax请求 获取部门信息
                getDepts("#empAddModal select");

                $("#empAddModal").modal({
                    backdrop: "static"
                });
            });

            $("#empName_add_input").change(function () {
                var empName = this.value;
                //发送ajax请求  校验用户名
                $.ajax({
                    url: getRootPath() + "/checkuser",
                    data: "empName=" + empName,
                    type: "POST",
                    success: function (result) {
                        if (result.code == 100) {
                            show_validate_msg("#empName_add_input", "success", "用户名可用");
                            $("#emp_save_btn").attr("ajax-val", "success");
                        } else {
                            show_validate_msg("#empName_add_input", "error", result.json.val_msg);
                            $("#emp_save_btn").attr("ajax-val", "error");
                        }
                    }
                });
            });

            //保存按钮
            $("#emp_save_btn").click(function () {
                //校验数据
                if (!validate_add_form()) {
                    return false;
                }
                if ($(this).attr("ajax-val") == "error") {
                    return false;
                }


                //发送ajax请求 保存员工
                $.ajax({
                    url: getRootPath() + "/emp",
                    type: "POST",
                    data: $("#empAddModal form").serialize(),
                    success: function (result) {
                        if(result.code == 100){
                            //关闭模态框
                            $("#empAddModal").modal("hide");

                            //最后一页
                            to_page(totalRecord);
                        }else{
                            //显示JSR303校验失败信息
                            if(undefined != result.json.errorFields.email){
                                show_validate_msg("#email_add_input","error",result.json.errorFields.email);
                            }
                            if(undefined != result.json.errorFields.empName){
                                show_validate_msg("#empName_add_input","error",result.json.errorFields.empName);
                            }
                        }
                    }
                });
            });
        });

        //发送ajax请求  获取部门信息
        function getDepts(element) {
            $(element).empty();
            $.ajax({
                url: getRootPath() + "/depts",
                type: "GET",
                success: function (result) {
                    $.each(result.json.depts, function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                        optionElement.appendTo(element);
                    });
                }
            });
        }

        //校验数据
        function validate_add_form() {
            var empName = $("#empName_add_input").val();
            var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
            if (!regName.test(empName)) {
                show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
                return false;
            } else {
                show_validate_msg("#empName_add_input", "success", "");
            }

            var email = $("#email_add_input").val();
            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if (!regEmail.test(email)) {
                show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
                return false;
            } else {
                show_validate_msg("#email_add_input", "success", "");
            }
            return true;
        }
        function show_validate_msg(element, status, message) {
            $(element).parent().removeClass("has-success has-error");
            $(element).next("span").text("");
            if ("success" == status) {
                $(element).parent().addClass("has-success");
                $(element).next("span").text(message);

            } else if ("error" == status) {
                $(element).parent().addClass("has-error");
                $(element).next("span").text(message);
            }
        }
        //重置表单
        function reset_form(element) {
            $(element)[0].reset();
            $(element).find("*").removeClass("has-error has-success");
            $(element).find(".help-block").text("");
        }

        //编辑按钮
        $(document).on("click",".edit_btn",function () {
            //获取部门信息
            getDepts("#empUpdateModal select");
            //获取员工信息
            getEmp($(this).attr("edit-id"));

            //员工id存到模态框的更新按钮上
            $("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"))

            //显示修改员工的模态框
            $("#empUpdateModal").modal({
                backdrop:"static"
            });
        });
        
        function getEmp(id) {
            $.ajax({
                url:getRootPath()+"/emp/"+id,
                type:"GET",
                success:function (result) {
                    var emp = result.json.employee;
                    $("#empName_update_static").text(emp.empName);
                    $("#email_update_input").val(emp.email);
                    $("#empUpdateModal input[name=gender]").val([emp.gender]);
                    $("#empUpdateModal select").val([emp.dId]);
                }
            });
        }

        $(function () {
          	//更新按钮
            $("#emp_update_btn").click(function () {
                //校验邮箱信息
                var email = $("#email_update_input").val();
                var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
                if (!regEmail.test(email)) {
                    show_validate_msg("#email_update_input", "error", "邮箱格式不正确");
                    return false;
                } else {
                    show_validate_msg("#email_update_input", "success", "");
                }

                //发送ajax请求 保存员工
                $.ajax({
                    url:getRootPath()+"/emp/"+$(this).attr("edit-id"),
                    type:"PUT",
                    data:$("#empUpdateModal form").serialize(),
                    success:function(result) {
                        //关闭更新模态框
                        $("#empUpdateModal").modal("hide");
                      	//跳转到当前页
                        to_page(currentPage);
                    }
                });
            });
        });
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改员工的 模态框 -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">员工修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_update_static" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_update_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_update_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### EmployeeControllere.java

```java
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
		if(result.hasErrors()){
			Map<String,Object> map = new HashMap<>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields",map);
		}else{
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
		if(!empName.matches(regx)){
			return Message.fail().add("val_msg","用户名必须是2-5位中文或者6-16位英文和数字的组合");
		}

		//数据库用户名重复校验
		boolean flag = employeeService.checkUser(empName);
		if (flag) {
			return Message.success();
		} else {
			return Message.fail().add("val_msg","用户名不可用");
		}
	}

	/**
	 * 根据员工id查询员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Message getEmp(@PathVariable("id")Integer id){
		Employee employee = employeeService.getEmp(id);
		return Message.success().add("employee",employee);
	}

	/**
	 * 更新员工
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	@ResponseBody
	public Message saveEmp(Employee employee){
		Logger logger = LoggerFactory.getLogger(EmployeeController.class);
		logger.debug(employee.getEmpId()+"");
		employeeService.updateEmp(employee);
		return Message.success();
	}
}
```

### EmployeeSerevice.java

```java
package com.crud.service;

import com.crud.bean.Employee;
import com.crud.bean.EmployeeExample;
import com.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 查询所有员工信息
	 *
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * 保存员工
	 *
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 检验用户名是否可用
	 *
	 * @param empName
	 * @return true：用户名可用  false：用户名不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}

	/**
	 * 根据员工id查询员工
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
}
```



## 单个删除员工信息

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath() {
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        //总记录数
        var totalRecord;
        //当前页
        var currentPage;

        /*页面加载完成，发起请求：获取分页数据*/
        $(function () {
            to_page(1);
        });

        function to_page(pageNumer) {
            $.ajax({
                url: getRootPath() + "/emps",
                data: {
                    pageNumber: pageNumer
                },
                type: "GET",
                success: function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }

        //显示员工数据
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees, function (index, item) {
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M" ? "男" : "女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                editBtn.attr("edit-id",item.empId);
                var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                delBtn.attr("delete-id",item.empId);
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };

        //显示分页文字
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 " + result.json.pageInfo.pageNum + " 页，总 " + result.json.pageInfo.pages + " 页，总 " + result.json.pageInfo.total + " 条记录");

            totalRecord = result.json.pageInfo.total;
            currentPage = result.json.pageInfo.pageNum;
        };

        //显示分页条
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&laquo;")));
            if (result.json.pageInfo.hasPreviousPage == false) {
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            } else {
                firstPageLi.click(function () {
                    to_page(1);
                });
                prePageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum - 1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if (result.json.pageInfo.pageNum == item) {
                    numLi.addClass("active");
                }
                numLi.click(function () {
                    to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));
            if (result.json.pageInfo.hasNextPage == false) {
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            } else {
                nextPageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum + 1);
                });
                lastPageLi.click(function () {
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function () {
            //新增按钮
            $("#emp_add_modal_btn").click(function () {
                //清除表单数据（表单数据、表单样式）
                reset_form("#empAddModal form");

                //发送ajax请求 获取部门信息
                getDepts("#empAddModal select");

                $("#empAddModal").modal({
                    backdrop: "static"
                });
            });

            $("#empName_add_input").change(function () {
                var empName = this.value;
                //发送ajax请求  校验用户名
                $.ajax({
                    url: getRootPath() + "/checkuser",
                    data: "empName=" + empName,
                    type: "POST",
                    success: function (result) {
                        if (result.code == 100) {
                            show_validate_msg("#empName_add_input", "success", "用户名可用");
                            $("#emp_save_btn").attr("ajax-val", "success");
                        } else {
                            show_validate_msg("#empName_add_input", "error", result.json.val_msg);
                            $("#emp_save_btn").attr("ajax-val", "error");
                        }
                    }
                });
            });

            //保存按钮
            $("#emp_save_btn").click(function () {
                //校验数据
                if (!validate_add_form()) {
                    return false;
                }
                if ($(this).attr("ajax-val") == "error") {
                    return false;
                }


                //发送ajax请求 保存员工
                $.ajax({
                    url: getRootPath() + "/emp",
                    type: "POST",
                    data: $("#empAddModal form").serialize(),
                    success: function (result) {
                        if(result.code == 100){
                            //关闭模态框
                            $("#empAddModal").modal("hide");

                            //最后一页
                            to_page(totalRecord);
                        }else{
                            //显示JSR303校验失败信息
                            if(undefined != result.json.errorFields.email){
                                show_validate_msg("#email_add_input","error",result.json.errorFields.email);
                            }
                            if(undefined != result.json.errorFields.empName){
                                show_validate_msg("#empName_add_input","error",result.json.errorFields.empName);
                            }
                        }
                    }
                });
            });
        });

        //发送ajax请求  获取部门信息
        function getDepts(element) {
            $(element).empty();
            $.ajax({
                url: getRootPath() + "/depts",
                type: "GET",
                success: function (result) {
                    $.each(result.json.depts, function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                        optionElement.appendTo(element);
                    });
                }
            });
        }

        //校验数据
        function validate_add_form() {
            var empName = $("#empName_add_input").val();
            var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
            if (!regName.test(empName)) {
                show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
                return false;
            } else {
                show_validate_msg("#empName_add_input", "success", "");
            }

            var email = $("#email_add_input").val();
            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if (!regEmail.test(email)) {
                show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
                return false;
            } else {
                show_validate_msg("#email_add_input", "success", "");
            }
            return true;
        }
        function show_validate_msg(element, status, message) {
            $(element).parent().removeClass("has-success has-error");
            $(element).next("span").text("");
            if ("success" == status) {
                $(element).parent().addClass("has-success");
                $(element).next("span").text(message);

            } else if ("error" == status) {
                $(element).parent().addClass("has-error");
                $(element).next("span").text(message);
            }
        }
        //重置表单
        function reset_form(element) {
            $(element)[0].reset();
            $(element).find("*").removeClass("has-error has-success");
            $(element).find(".help-block").text("");
        }

        //编辑按钮
        $(document).on("click",".edit_btn",function () {
            //获取部门信息
            getDepts("#empUpdateModal select");
            //获取员工信息
            getEmp($(this).attr("edit-id"));

            //员工id存到模态框的更新按钮上
            $("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"))

            //显示修改员工的模态框
            $("#empUpdateModal").modal({
                backdrop:"static"
            });
        });
        
        function getEmp(id) {
            $.ajax({
                url:getRootPath()+"/emp/"+id,
                type:"GET",
                success:function (result) {
                    var emp = result.json.employee;
                    $("#empName_update_static").text(emp.empName);
                    $("#email_update_input").val(emp.email);
                    $("#empUpdateModal input[name=gender]").val([emp.gender]);
                    $("#empUpdateModal select").val([emp.dId]);
                }
            });
        }

        //更新按钮
        $(function () {
            $("#emp_update_btn").click(function () {
                //校验邮箱信息
                var email = $("#email_update_input").val();
                var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
                if (!regEmail.test(email)) {
                    show_validate_msg("#email_update_input", "error", "邮箱格式不正确");
                    return false;
                } else {
                    show_validate_msg("#email_update_input", "success", "");
                }

                //发送ajax请求 保存员工
                $.ajax({
                    url:getRootPath()+"/emp/"+$(this).attr("edit-id"),
                    type:"PUT",
                    data:$("#empUpdateModal form").serialize(),
                    success:function(result) {
                        //关闭更新模态框
                        $("#empUpdateModal").modal("hide");
                        to_page(currentPage);
                    }
                });
            });
        });

        //删除按钮
        $(document).on("click",".delete_btn",function (){
            var empName = $(this).parents("tr").find("td:eq(1)").text();
            var empId = $(this).attr("delete-id");
            if(confirm("确认删除【"+empName+"】吗？")){
                $.ajax({
                    url:getRootPath()+"/emp/"+empId,
                    type:"DELETE",
                    success:function (result) {
                        to_page(currentPage);
                    }
                });
            }
        });
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改员工的 模态框 -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">员工修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_update_static" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_update_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_update_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### EmployeeController.java

```java
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
		if(result.hasErrors()){
			Map<String,Object> map = new HashMap<>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields",map);
		}else{
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
		if(!empName.matches(regx)){
			return Message.fail().add("val_msg","用户名必须是2-5位中文或者6-16位英文和数字的组合");
		}

		//数据库用户名重复校验
		boolean flag = employeeService.checkUser(empName);
		if (flag) {
			return Message.success();
		} else {
			return Message.fail().add("val_msg","用户名不可用");
		}
	}

	/**
	 * 根据员工id查询员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Message getEmp(@PathVariable("id")Integer id){
		Employee employee = employeeService.getEmp(id);
		return Message.success().add("employee",employee);
	}

	/**
	 * 更新员工
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	@ResponseBody
	public Message saveEmp(Employee employee){
		Logger logger = LoggerFactory.getLogger(EmployeeController.class);
		logger.debug(employee.getEmpId()+"");
		employeeService.updateEmp(employee);
		return Message.success();
	}

	/**
	 * 删除员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public Message deleteEmpById(@PathVariable("id")Integer id){
		employeeService.deleteEmp(id);
		return Message.success();
	}
}
```

### EmployeeService.java

```java
package com.crud.service;

import com.crud.bean.Employee;
import com.crud.bean.EmployeeExample;
import com.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 查询所有员工信息
	 *
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * 保存员工
	 *
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 检验用户名是否可用
	 *
	 * @param empName
	 * @return true：用户名可用  false：用户名不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}

	/**
	 * 根据员工id查询员工
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * 删除员工
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
}
```



## 批量删除员工信息

### index.html

```html
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>员工列表</title>

    <!-- 引入 Bootstrap 样式 -->
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <!-- 引入Jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- 引入 Bootstrap 样式 -->
    <script type="text/javascript" src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //获取项目路径
        function getRootPath() {
            var pathName = window.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return projectName;
        }

        //总记录数
        var totalRecord;
        //当前页
        var currentPage;

        /*页面加载完成，发起请求：获取分页数据*/
        $(function () {
            to_page(1);
        });

        function to_page(pageNumer) {
            $("#check_all").prop("checked", false);
            $.ajax({
                url: getRootPath() + "/emps",
                data: {
                    pageNumber: pageNumer
                },
                type: "GET",
                success: function (result) {
                    //显示员工数据
                    build_emps_table(result);
                    //显示分页文字
                    build_page_info(result);
                    //显示分页条
                    build_page_nav(result);
                }
            });
        }

        //显示员工数据
        function build_emps_table(result) {
            //清空table表格
            $("#emps_table tbody").empty();

            var employees = result.json.pageInfo.list;
            $.each(employees, function (index, item) {
                var checkBox = $("<td><input type='checkbox' class='check_item'></td>");
                var empId = $("<td></td>").append(item.empId);
                var empName = $("<td></td>").append(item.empName);
                var gender = $("<td></td>").append(item.gender == "M" ? "男" : "女");
                var email = $("<td></td>").append(item.email);
                var deptName = $("<td></td>").append(item.department.deptName);
                var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
                editBtn.attr("edit-id", item.empId);
                var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                    .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
                delBtn.attr("delete-id", item.empId);
                var btn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
                $("<tr></tr>").append(checkBox)
                    .append(empId)
                    .append(empName)
                    .append(gender)
                    .append(email)
                    .append(deptName)
                    .append(btn)
                    .appendTo($("#emps_table tbody"));
            });
        };

        //显示分页文字
        function build_page_info(result) {
            //清空
            $("#page_info_area").empty();

            $("#page_info_area").append("当前第 " + result.json.pageInfo.pageNum + " 页，总 " + result.json.pageInfo.pages + " 页，总 " + result.json.pageInfo.total + " 条记录");

            totalRecord = result.json.pageInfo.total;
            currentPage = result.json.pageInfo.pageNum;
        };

        //显示分页条
        function build_page_nav(result) {
            //清空
            $("#page_nav_area").empty();

            var ul = $("<ul></ul>").addClass("pagination");
            //首页
            var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
            //前一页
            var prePageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&laquo;")));
            if (result.json.pageInfo.hasPreviousPage == false) {
                prePageLi.addClass("disabled");
                firstPageLi.addClass("disabled");
            } else {
                firstPageLi.click(function () {
                    to_page(1);
                });
                prePageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum - 1);
                });
            }

            ul.append(firstPageLi).append(prePageLi);

            $.each(result.json.pageInfo.navigatepageNums, function (index, item) {
                var numLi = $("<li></li>").append($("<a></a>").append(item));
                if (result.json.pageInfo.pageNum == item) {
                    numLi.addClass("active");
                }
                numLi.click(function () {
                    to_page(item);
                });
                ul.append(numLi);
            });

            //后一页
            var nextPageLi = $("<li></li>").append($("<a></a>").attr("href", "#").append($("<span></span>").append("&raquo;")));
            //尾页
            var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));
            if (result.json.pageInfo.hasNextPage == false) {
                nextPageLi.addClass("disabled");
                lastPageLi.addClass("disabled");
            } else {
                nextPageLi.click(function () {
                    to_page(result.json.pageInfo.pageNum + 1);
                });
                lastPageLi.click(function () {
                    to_page(result.json.pageInfo.pages);
                });
            }

            ul.append(nextPageLi).append(lastPageLi);

            var nav = $("<nav></nav>").append(ul).appendTo("#page_nav_area");
        };


        /* 新增员工 */
        $(function () {
            //新增按钮
            $("#emp_add_modal_btn").click(function () {
                //清除表单数据（表单数据、表单样式）
                reset_form("#empAddModal form");

                //发送ajax请求 获取部门信息
                getDepts("#empAddModal select");

                $("#empAddModal").modal({
                    backdrop: "static"
                });
            });

            $("#empName_add_input").change(function () {
                var empName = this.value;
                //发送ajax请求  校验用户名
                $.ajax({
                    url: getRootPath() + "/checkuser",
                    data: "empName=" + empName,
                    type: "POST",
                    success: function (result) {
                        if (result.code == 100) {
                            show_validate_msg("#empName_add_input", "success", "用户名可用");
                            $("#emp_save_btn").attr("ajax-val", "success");
                        } else {
                            show_validate_msg("#empName_add_input", "error", result.json.val_msg);
                            $("#emp_save_btn").attr("ajax-val", "error");
                        }
                    }
                });
            });

            //保存按钮
            $("#emp_save_btn").click(function () {
                //校验数据
                if (!validate_add_form()) {
                    return false;
                }
                if ($(this).attr("ajax-val") == "error") {
                    return false;
                }


                //发送ajax请求 保存员工
                $.ajax({
                    url: getRootPath() + "/emp",
                    type: "POST",
                    data: $("#empAddModal form").serialize(),
                    success: function (result) {
                        if (result.code == 100) {
                            //关闭模态框
                            $("#empAddModal").modal("hide");

                            //最后一页
                            to_page(totalRecord);
                        } else {
                            //显示JSR303校验失败信息
                            if (undefined != result.json.errorFields.email) {
                                show_validate_msg("#email_add_input", "error", result.json.errorFields.email);
                            }
                            if (undefined != result.json.errorFields.empName) {
                                show_validate_msg("#empName_add_input", "error", result.json.errorFields.empName);
                            }
                        }
                    }
                });
            });
        });

        //发送ajax请求  获取部门信息
        function getDepts(element) {
            $(element).empty();
            $.ajax({
                url: getRootPath() + "/depts",
                type: "GET",
                success: function (result) {
                    $.each(result.json.depts, function () {
                        var optionElement = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                        optionElement.appendTo(element);
                    });
                }
            });
        }

        //校验数据
        function validate_add_form() {
            var empName = $("#empName_add_input").val();
            var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
            if (!regName.test(empName)) {
                show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
                return false;
            } else {
                show_validate_msg("#empName_add_input", "success", "");
            }

            var email = $("#email_add_input").val();
            var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if (!regEmail.test(email)) {
                show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
                return false;
            } else {
                show_validate_msg("#email_add_input", "success", "");
            }
            return true;
        }

        function show_validate_msg(element, status, message) {
            $(element).parent().removeClass("has-success has-error");
            $(element).next("span").text("");
            if ("success" == status) {
                $(element).parent().addClass("has-success");
                $(element).next("span").text(message);

            } else if ("error" == status) {
                $(element).parent().addClass("has-error");
                $(element).next("span").text(message);
            }
        }

        //重置表单
        function reset_form(element) {
            $(element)[0].reset();
            $(element).find("*").removeClass("has-error has-success");
            $(element).find(".help-block").text("");
        }

        //编辑按钮
        $(document).on("click", ".edit_btn", function () {
            //获取部门信息
            getDepts("#empUpdateModal select");
            //获取员工信息
            getEmp($(this).attr("edit-id"));

            //员工id存到模态框的更新按钮上
            $("#emp_update_btn").attr("edit-id", $(this).attr("edit-id"))

            //显示修改员工的模态框
            $("#empUpdateModal").modal({
                backdrop: "static"
            });
        });

        function getEmp(id) {
            $.ajax({
                url: getRootPath() + "/emp/" + id,
                type: "GET",
                success: function (result) {
                    var emp = result.json.employee;
                    $("#empName_update_static").text(emp.empName);
                    $("#email_update_input").val(emp.email);
                    $("#empUpdateModal input[name=gender]").val([emp.gender]);
                    $("#empUpdateModal select").val([emp.dId]);
                }
            });
        }

        //更新按钮
        $(function () {
            $("#emp_update_btn").click(function () {
                //校验邮箱信息
                var email = $("#email_update_input").val();
                var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
                if (!regEmail.test(email)) {
                    show_validate_msg("#email_update_input", "error", "邮箱格式不正确");
                    return false;
                } else {
                    show_validate_msg("#email_update_input", "success", "");
                }

                //发送ajax请求 保存员工
                $.ajax({
                    url: getRootPath() + "/emp/" + $(this).attr("edit-id"),
                    type: "PUT",
                    data: $("#empUpdateModal form").serialize(),
                    success: function (result) {
                        //关闭更新模态框
                        $("#empUpdateModal").modal("hide");
                        to_page(currentPage);
                    }
                });
            });
        });

        //单个删除按钮
        $(document).on("click", ".delete_btn", function () {
            var empName = $(this).parents("tr").find("td:eq(1)").text();
            var empId = $(this).attr("delete-id");
            if (confirm("确认删除【" + empName + "】吗？")) {
                $.ajax({
                    url: getRootPath() + "/emp/" + empId,
                    type: "DELETE",
                    success: function (result) {
                        to_page(currentPage);
                    }
                });
            }
        });

        //全选/全不选
        $(function () {
            $("#check_all").click(function () {
                $(".check_item").prop("checked", $(this).prop("checked"));
            });
        });
        $(document).on("click", ".check_item", function () {
            var flag = $(".check_item:checked").length == $(".check_item").length;
            $("#check_all").prop("checked", flag);
        });

        //批量删除按钮
        $(function () {
            $("#emp_delete_all_btn").click(function () {
                var empNames = "";
                var del_idstr = "";
                $.each($(".check_item:checked"), function () {
                    empNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
                    del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
                });
                empNames = empNames.substring(0, empNames.length - 1);
                del_idstr = del_idstr.substring(0, del_idstr.length - 1);
                if (confirm("确认删除【" + empNames + "】")) {
                    //发送ajax请求 批量删除员工
                    $.ajax({
                        url: getRootPath() + "/emp/" + del_idstr,
                        type: "DELETE",
                        success: function (result) {
                            alert(result.message);
                            to_page(currentPage);
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>

<!-- 新增员工的 模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_add_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改员工的 模态框 -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">员工修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_update_static" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_update_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" id="email_update_input"
                                   placeholder="email">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked">
                                男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="W"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <!-- 部门id -->
                            <select class="form-control" name="dId">
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM_CRUD</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
        </div>
    </div>
    <!--表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" id="check_all"/>
                    </th>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--分页信息-->
    <div class="row">
        <!--分页文字-->
        <div class="col-md-6" id="page_info_area"></div>
        <!--分页条-->
        <div class="col-md-6" id="page_nav_area"></div>
    </div>
</div>
</body>
</html>
```

### EmployeeController.java

```java
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
```

### EmployeeService.java

```java
package com.crud.service;

import com.crud.bean.Employee;
import com.crud.bean.EmployeeExample;
import com.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 查询所有员工信息
	 *
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * 保存员工
	 *
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 检验用户名是否可用
	 *
	 * @param empName
	 * @return true：用户名可用  false：用户名不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}

	/**
	 * 根据员工id查询员工
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * 删除员工
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除员工
	 * @param ids
	 */
	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
	}
}
```







