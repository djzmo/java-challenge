package jp.co.axa.apidemo.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuccessWithDataResponse<T> {
    private boolean success;
    private T data;
}
