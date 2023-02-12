package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.entities.EmployeeDto;
import jp.co.axa.apidemo.dto.requests.UpdateEmployeeRequest;
import jp.co.axa.apidemo.dto.responses.SuccessResponse;
import jp.co.axa.apidemo.dto.responses.SuccessWithDataResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.dto.requests.SaveEmployeeRequest;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        List<EmployeeDto> response = employees.stream().map(EmployeeDto::fromJpa).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return new ResponseEntity<>(EmployeeDto.fromJpa(employee), employee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody SaveEmployeeRequest request) {
        Employee result = employeeService.saveEmployee(request);
        return new ResponseEntity<>(EmployeeDto.fromJpa(result), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<SuccessResponse> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        boolean result = employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(new SuccessResponse(result), result ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<SuccessWithDataResponse<EmployeeDto>> updateEmployee(@Valid @RequestBody UpdateEmployeeRequest request,
                                                                               @PathVariable(name = "employeeId") Long employeeId) {
        Employee updated = employeeService.updateEmployee(employeeId, request);
        boolean success = updated != null;
        return new ResponseEntity<>(new SuccessWithDataResponse<>(success, EmployeeDto.fromJpa(updated)), success ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
