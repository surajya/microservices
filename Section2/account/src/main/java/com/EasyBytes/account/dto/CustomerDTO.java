package com.EasyBytes.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
@Schema(
    name="Customer",
    description="Schema to hold customer and account information"
)
public class CustomerDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, message = "Name should have at least 2 characters")
    @Schema(
        description = "Name of the customer",
        example = "John Doe"
    )
    private String name;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email should not be empty")
    @Schema(
        description = "Email of the customer",
        example = "p2YK0@example.com")
    private String email;

    @Pattern(regexp = "^[0-9]{10}", message = "Invalid mobile number")
    @NotEmpty(message = "Mobile number should not be empty")
    @Schema(
        description = "Mobile number of the customer",
        example = "1234567890"
    )
    private String mobileNumber;

    private AccountDTO accountDto;
}