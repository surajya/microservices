package com.EasyBytes.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor

@Schema(
        name = "ErrorResponse",
        description = "schema to hold the Error Response information"
)
public class ErrorResponseDTO {

    @Schema(
            name = "apiPath",
            description = "API path where the error occurred"
    )
    private String apiPath;

    @Schema(
            name = "errorCode",
            description = "schema to hold the HTTP status code"
    )
    private HttpStatus errorCode;

    @Schema(
            name = "errorMessage",
            description = "schema to hold the Error message"
    )
    private String errorMessage;

    @Schema(
            name = "errorTime",
            description = "schema to hold the Error time"
    )
    private LocalDateTime errorTime;
}