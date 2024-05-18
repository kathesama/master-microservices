package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.master.microservices.service.account.application.ports.input.AccountServiceInputPort;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request.AccountRequestModel;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request.CustomerRequestModel;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.AccountResponse;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.CustomerResponse;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.AccountRestMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.CustomerRestMapper;

import com.kathesama.app.master.microservices.service.account.util.AccountsContactInfo;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ErrorResponseDto;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ResponseBasicModel;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Katherine Aguirre
 */

@Tag(
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {
    private final AccountServiceInputPort accountService;
    private final CustomerRestMapper customerMapper;
    private final AccountRestMapper accountMapper;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final AccountsContactInfo accountsContactInfo;


    @RateLimiter(name= "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/api/v1/info")
    public ResponseEntity<Object> getInfo() {
        log.debug("Invoked Account info API: Hot reload");
        Map<String, Object> body = new HashMap<>();
        body.put("Message", "Responding from Accounts microservice.");
        body.put("Build version", buildVersion);
        body.put("Java version", environment.getProperty("java.version"));
        body.put("Contact info ", accountsContactInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        log.debug("Invoked Account fallback info API");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 17 from rate limiter triggered");
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/api/v1/{mobileNumber}")
    public ResponseEntity<CustomerResponse> fetchAccountDetails(@PathVariable
                                                                    @Pattern(regexp="(^$|[0-9]{10})",
                                                                            message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(
                customerMapper.toCustomerResponse(
                        accountService.fetchAccount(mobileNumber)
                )
        );
    }

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside EazyBank"
    )
    @ApiResponses({
                @ApiResponse(
                        responseCode = "201",
                        description = "HTTP Status CREATED"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDto.class)
                        )
                )
        }
    )
    @PostMapping("/api/v1")
    public ResponseEntity<CustomerResponse> createAccount(@Valid @RequestBody CustomerRequestModel customerDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    customerMapper.toCustomerResponse(
                            accountService.createAccount(
                                customerMapper.toCustomer(customerDto)
            )));
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/api/v1/{customerId}")
    public ResponseEntity<AccountResponse> updateAccountDetails(@PathVariable
                                                                     @NotNull(message = "Customer ID must not be blank")
                                                                     @Digits(integer=10, fraction=0, message = "Customer ID must be a valid number")
                                                                     Long customerId,
                                                                @Valid @RequestBody AccountRequestModel accountRequestModel) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    accountMapper.toAccountResponse(
                            accountService.updateAccount(customerId, accountRequestModel)
                    )
            );
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/api/v1/{customerId}")
    public ResponseEntity<ResponseBasicModel> deleteAccountDetails(@PathVariable
                                                                @NotNull(message = "Customer ID must not be blank")
                                                                @Digits(integer=10, fraction=0, message = "Customer ID must be a valid number")
                                                                Long customerId) {
        accountService.deleteAccount(customerId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseBasicModel(SuccessCatalog.STATUS_200.getCode(), SuccessCatalog.STATUS_200.getMessage()));
    }
}
