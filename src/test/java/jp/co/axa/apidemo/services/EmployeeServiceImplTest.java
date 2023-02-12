package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.requests.SaveEmployeeRequest;
import jp.co.axa.apidemo.dto.requests.UpdateEmployeeRequest;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void testRetrieveEmployees() {
        // Given
        when(employeeRepository.findAll()).thenReturn(new ArrayList<Employee>() {{
            add(new Employee(1L, "John Doe", 1000, "CEO"));
            add(new Employee(2L, "David Smith", 500, "CTO"));
        }});

        // Do
        List<Employee> result = employeeService.retrieveEmployees();

        // Verify
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId().longValue());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals(1000, result.get(0).getSalary().intValue());
        assertEquals("CEO", result.get(0).getDepartment());
        assertEquals(2L, result.get(1).getId().longValue());
        assertEquals("David Smith", result.get(1).getName());
        assertEquals(500, result.get(1).getSalary().intValue());
        assertEquals("CTO", result.get(1).getDepartment());
    }

    @Test
    public void testGetEmployee() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee(1L, "John Doe", 1000, "CEO")));

        // Do
        Employee result = employeeService.getEmployee(1L);

        // Verify
        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getName());
        assertEquals(1000, result.getSalary().intValue());
        assertEquals("CEO", result.getDepartment());
    }

    @Test
    public void testSaveEmployee() {
        // Given
        SaveEmployeeRequest request = new SaveEmployeeRequest("John Doe", 1000, "CEO");
        Employee employee = new Employee(1L, "John Doe", 1000, "CEO");
        when(employeeRepository.save(any())).thenReturn(employee);

        // Do
        Employee result = employeeService.saveEmployee(request);

        // Verify
        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getName());
        assertEquals(1000, result.getSalary().intValue());
        assertEquals("CEO", result.getDepartment());
    }

    @Test
    public void testDeleteEmployee() {
        // Given
        Employee employee = new Employee(1L, "John Doe", 1000, "CEO");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Do
        boolean result = employeeService.deleteEmployee(1L);

        // Verify
        assertTrue(result);
    }

    @Test
    public void testUpdateEmployee() {
        // Given
        UpdateEmployeeRequest request = new UpdateEmployeeRequest("John Doe", 1000, "CEO");
        Employee employee = new Employee(1L, "John Doe", 1000, "CEO");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);

        // Do
        Employee result = employeeService.updateEmployee(1L, request);

        // Verify
        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getName());
        assertEquals(1000, result.getSalary().intValue());
        assertEquals("CEO", result.getDepartment());
    }
}
