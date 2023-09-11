package com.hngTask2.identify.data.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private Long id;
    private String message;
    private boolean isSuccess;
}
