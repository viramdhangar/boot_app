package com.waio.service;

import java.util.List;

import com.waio.model.Employee;

public interface IEmployeeService {
	void insertEmployee(Employee emp);
	void insertEmployees(List<Employee> employees);
	List<Employee> getAllEmployees();
	void getEmployeeById(String empid);
}