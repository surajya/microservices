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
        description="schema to hold Accounts information"
)
public class AccountsDto {

    @Pattern(regexp = "^[0-9]{10}", message = "Invalid Accounts number")
    @NotEmpty(message = "Accounts number is required")
    @Schema(
        description = "Accounts number of EasyBank Accounts",
        example = "1234567890"
    )
    private Long accountNumber;

    @NotEmpty(message = "Accounts type is required")
    @Schema(
            description = "Accounts type of EasyBank Accounts",
            example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch address is required")
    @Schema(
            description = "Branch address of EasyBank Accounts",
            example = "123 Main Street, New York"
    )
    private String branchAddress;
}