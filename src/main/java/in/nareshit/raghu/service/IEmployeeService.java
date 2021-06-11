package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.model.Employee;

public interface IEmployeeService {
	Integer createEmployee(Employee e);
	List<Employee> getAllEmployees();
}
