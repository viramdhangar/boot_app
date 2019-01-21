package com.waio.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.waio.dao.IEmployeeDao;
import com.waio.model.Employee;

@Repository("EmployeeDao")
public class EmployeeDao extends JdbcDaoSupport implements IEmployeeDao{
	
	@Autowired 
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	@Override
	public void insertEmployee(Employee emp) {
		String sql = "INSERT INTO employee " +
				"(empId, empName) VALUES (?, ?)" ;
		getJdbcTemplate().update(sql, new Object[]{
				emp.getEmpId(), emp.getEmpName()
		});
	}
	
	@Override
	public void insertEmployees(final List<Employee> employees) {
		String sql = "INSERT INTO employee " + "(empId, empName) VALUES (?, ?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Employee employee = employees.get(i);
				ps.setString(1, employee.getEmpId());
				ps.setString(2, employee.getEmpName());
			}
			
			public int getBatchSize() {
				return employees.size();
			}
		});

	}
	@Override
	public List<Employee> getAllEmployees(){
		String sql = "SELECT * FROM employee";
		List<Employee> result = new ArrayList<Employee>();
		result = getJdbcTemplate().query(sql, new Object[] {}, new BeanPropertyRowMapper<Employee>(Employee.class));
		return result;
	}

	@Override
	public Employee getEmployeeById(String empId) {
		String sql = "SELECT * FROM employee WHERE empId = ?";
		return (Employee)getJdbcTemplate().queryForObject(sql, new Object[]{empId}, new RowMapper<Employee>(){
			@Override
			public Employee mapRow(ResultSet rs, int rwNumber) throws SQLException {
				Employee emp = new Employee();
				emp.setEmpId(rs.getString("empId"));
				emp.setEmpName(rs.getString("empName"));
				return emp;
			}
		});
	}
}