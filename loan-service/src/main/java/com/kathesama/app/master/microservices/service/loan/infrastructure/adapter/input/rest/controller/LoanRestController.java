package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.ErrorResponseDto;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.ResponseBasicModel;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
import com.kathesama.app.master.microservices.service.loan.application.ports.input.LoanServiceInputPort;
import com.kathesama.app.master.microservices.service.loan.domain.model.Loan;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.dto.model.request.LoanRequestModel;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.mapper.LoanRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoanRestController {
    private final LoanServiceInputPort service;
    private final LoanRestMapper mapper;

    @GetMapping("/api/v1/hello")
    public String helloWorld() {
        return "Hello loan";
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Loan details based on a mobile number"
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
    public ResponseEntity<LoanResponseModel> fetchAccountDetails(@PathVariable
                                                                @Pattern(regexp="(^$|[0-9]{10})",
                                                                        message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        Loan card = service.fetch(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.toLoanResponse(card)
        );
    }

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new Loan inside EazyBank"
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
    @PostMapping("/api/v1/{mobileNumber}")
    public ResponseEntity<LoanResponseModel> createAccount(@PathVariable
                                                              @NotEmpty(message = "Customer mobile number must not be blank")
                                                              @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                              String mobileNumber) {
        return ResponseEntity
            .status(Integer.parseInt(SuccessCatalog.STATUS_200.getCode()))
            .body(
                mapper.toLoanResponse(
                        service.createLoan(mobileNumber)
                )
            );
    }

    @Operation(
            summary = "Update Loan Details REST API",
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
    @PutMapping("/api/v1/{mobileNumber}")
    public ResponseEntity<LoanResponseModel> updateAccountDetails(@PathVariable
                                                                    @NotEmpty(message = "Customer mobile number must not be blank")
                                                                    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                    String mobileNumber,
                                                                @Valid @RequestBody LoanRequestModel cardRequestModel) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        mapper.toLoanResponse(
                                service.update(mobileNumber, mapper.toLoan(cardRequestModel))
                        )
                );
    }

    @Operation(
            summary = "Delete card Details REST API",
            description = "REST API to delete card details based on a mobile number"
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
    @DeleteMapping("/api/v1/{mobileNumber}")
    public ResponseEntity<ResponseBasicModel> deleteAccountDetails(@PathVariable
                                                                       @NotEmpty(message = "Customer mobile number must not be blank")
                                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                       String mobileNumber) {
        service.delete(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseBasicModel(SuccessCatalog.STATUS_200.getCode(), SuccessCatalog.STATUS_200.getMessage()));
    }
}
