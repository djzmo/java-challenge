package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetEmployees() throws Exception {
        when(employeeService.retrieveEmployees()).thenReturn(
                Arrays.asList(new Employee(1L, "John Doe", 10000, "CEO"),
                        new Employee(2L, "David Smith", 5000, "CTO"))
        );

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].salary", is(10000)))
                .andExpect(jsonPath("$[0].department", is("CEO")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("David Smith")))
                .andExpect(jsonPath("$[1].salary", is(5000)))
                .andExpect(jsonPath("$[1].department", is("CTO")));
    }

    @Test
    public void testGetEmployee() throws Exception {
        when(employeeService.getEmployee(1L)).thenReturn(
                new Employee(1L, "John Doe", 10000, "CEO")
        );

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("John Doe")))
                .andExpect(jsonPath("$.data.salary", is(10000)))
                .andExpect(jsonPath("$.data.department", is("CEO")));
    }

    @Test
    public void testSaveEmployee() throws Exception {
        when(employeeService.saveEmployee(any())).thenReturn(
                new Employee(1L, "John Doe", 10000, "CEO")
        );

        String body = new JSONObject()
                .put("name", "John Doe")
                .put("salary", 10000)
                .put("department", "CEO")
                .toString();

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.salary", is(10000)))
                .andExpect(jsonPath("$.department", is("CEO")));
    }

    @Test
    public void testSaveEmployeeValid() throws Exception {
        // Test with empty body (name is mandatory)
        String body = new JSONObject()
                .toString();

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(any())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any())).thenReturn(
                new Employee(1L, "John Doe", 10000, "CEO"));

        String body = new JSONObject()
                .put("name", "John Doe")
                .put("salary", 10000)
                .put("department", "CEO")
                .toString();

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.name", is("John Doe")))
                .andExpect(jsonPath("$.data.salary", is(10000)))
                .andExpect(jsonPath("$.data.department", is("CEO")));
    }

    @Test
    public void testUpdateEmployeeValid() throws Exception {
        // Test with empty body (name is mandatory)
        String body = new JSONObject()
                .toString();

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }
}
