package jp.co.axa.apidemo.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveEmployeeRequest {
    @NotBlank
    private String name;
    private Integer salary;
    private String department;
}
