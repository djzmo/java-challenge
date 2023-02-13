package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.requests.SaveEmployeeRequest;
import jp.co.axa.apidemo.dto.requests.UpdateEmployeeRequest;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable(value = "employee")
    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employee", key = "#id", unless = "#result == null")
    public Employee getEmployee(Long id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        return optEmp.orElse(null);
    }

    public Employee saveEmployee(SaveEmployeeRequest request){
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setSalary(request.getSalary());
        employee.setDepartment(request.getDepartment());
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employee", key = "#id")
    public boolean deleteEmployee(Long id){
        Employee existingData = getEmployee(id);
        if (existingData != null) {
            employeeRepository.deleteById(id);
        } else {
            log.warn("Tried to delete non-existent Employee with id {}", id);
        }
        return existingData != null;
    }

    @CacheEvict(value = "employee", key = "#id")
    public Employee updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee existingData = getEmployee(id);
        if (existingData != null) {
            if (request.getName() != null) {
                existingData.setName(request.getName());
            }
            if (request.getSalary() != null) {
                existingData.setSalary(request.getSalary());
            }
            if (request.getDepartment() != null) {
                existingData.setDepartment(request.getDepartment());
            }
            employeeRepository.save(existingData);
        } else {
            log.warn("Tried to update non-existent Employee with id {}", id);
        }
        return existingData;
    }
}
