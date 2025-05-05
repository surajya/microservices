package com.EasyBytes.account.controller;

import com.EasyBytes.account.constant.AccountsConstants;
import com.EasyBytes.account.dto.AccountsContactInfoDto;
import com.EasyBytes.account.dto.CustomerDTO;
import com.EasyBytes.account.dto.ErrorResponseDTO;
import com.EasyBytes.account.dto.ResponseDTO;
import com.EasyBytes.account.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
    name = "EasyBankRest API Documentation",
    description = "Create, Post, Update, Delete Account"
)
class AccountController {

    private final IAccountService iAccountService;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;
    private final AccountsContactInfoDto accountsContactInfoDto;

    @Autowired
    public AccountController(IAccountService iAccountService, Environment environment, AccountsContactInfoDto accountsContactInfoDto) {
        this.iAccountService = iAccountService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Operation(
        summary = "Create Account Details Rest API",
        description = "Rest API Create account and customer"
    )
    @ApiResponse(responseCode = "201", description = "Account Created Successfully")
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        iAccountService.CreateAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account Details Rest API",
            description = "Rest API Fetch Details of Account And Customer"
    )
    @ApiResponse(responseCode = "201", description = "Account Fetched Successfully")
    @GetMapping(path = "/fetch")
    public ResponseEntity<CustomerDTO> getAccount(@RequestParam
                                                      @Pattern(regexp = "^[0-9]{10}", message = "Invalid mobile number")
                                                      String mobileNumber) {
        CustomerDTO customerDto= iAccountService.fetchAccount(mobileNumber);
        System.out.println(customerDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PutMapping(path = "/update")
    @Operation(
            summary = "Update Account Details Rest API",
            description = "Rest API Update Details of Account And Customer according to mobile number"
    )

    @ApiResponses({
                     @ApiResponse(
                    responseCode = "200",
                    description = "Account Update Successfully"
            ),

            @ApiResponse(
                    responseCode = "417",
                    description = "Exception Failed"
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "Account Not Update Successfully",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )}
    )

    public ResponseEntity<ResponseDTO> updateAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        boolean isUpdated = iAccountService.updateAccount(customerDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete Account Rest API",
            description = "Rest API Delete Account And Customer according to mobile number"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account Deleted Successfully"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Exception Failed"
            ),

            @ApiResponse(
            responseCode = "500",
            description = "Account Unable to Delete"
    )}
    )
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "^[0-9]{10}", message = "Invalid mobile number")
                                                         String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }


    @Operation(
            summary = "Fetch build version of Account Rest API",
            description = "Rest API Fetch build version of Account"
    )
    @ApiResponse(responseCode = "201", description = "build version Fetched Successfully")
    @GetMapping(path = "/build-info")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(buildVersion);
    }


    @Operation(
            summary = "Fetch java version of Account Rest API",
            description = "Rest API Fetch java info version of Account"
    )
    @ApiResponse(responseCode = "201", description = "build version Fetched Successfully")
    @GetMapping(path = "/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.ok(environment.getProperty("JAVA_HOME"));
    }


    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}