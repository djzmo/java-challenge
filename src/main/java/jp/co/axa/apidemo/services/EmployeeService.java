package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.requests.SaveEmployeeRequest;
import jp.co.axa.apidemo.dto.requests.UpdateEmployeeRequest;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> retrieveEmployees();

    Employee getEmployee(Long employeeId);

    Employee saveEmployee(SaveEmployeeRequest employee);

    boolean deleteEmployee(Long employeeId);

    Employee updateEmployee(Long employeeId, UpdateEmployeeRequest employee);
}
