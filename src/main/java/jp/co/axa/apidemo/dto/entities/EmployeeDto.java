package jp.co.axa.apidemo.dto.entities;

import jp.co.axa.apidemo.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {
    private Long id;
    @NotBlank
    private String name;
    private Integer salary;
    private String department;

    public static EmployeeDto fromJpa(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getSalary(), employee.getDepartment());
    }

    public static Employee toJpa(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setSalary(employeeDto.getSalary());
        return employee;
    }
}
