package com.EasyBytes.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(
        name="Accounts",
        description="schema to hold Account information"
)
public class AccountDTO {

    @Pattern(regexp = "^[0-9]{10}", message = "Invalid Account number")
    @NotEmpty(message = "Account number is required")
    @Schema(
        description = "Account number of EasyBank Account",
        example = "1234567890"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account type is required")
    @Schema(
            description = "Account type of EasyBank Account",
            example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch address is required")
    @Schema(
            description = "Branch address of EasyBank Account",
            example = "123 Main Street, New York"
    )
    private String branchAddress;
}