package jp.co.axa.apidemo.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateEmployeeRequest {
    private String name;
    private Integer salary;
    private String department;
}
