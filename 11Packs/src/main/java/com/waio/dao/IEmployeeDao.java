package com.waio.dao;

import java.util.List;

import com.waio.model.Employee;

public interface IEmployeeDao {
	void insertEmployee(Employee cus);
	void insertEmployees(List<Employee> employees);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(String empId);
}