package com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.request.LoanRequestModel;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ErrorResponseDto;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ResponseBasicModel;
import com.kathesama.app.master.microservices.service.common.util.common.ConstantsCatalog;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
import com.kathesama.app.master.microservices.service.loan.application.ports.input.LoanServiceInputPort;
import com.kathesama.app.master.microservices.service.common.domain.model.Loan;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.LoanResponseModel;
import com.kathesama.app.master.microservices.service.loan.infrastructure.adapter.input.rest.mapper.LoanRestMapper;
import com.kathesama.app.master.microservices.service.loan.util.LoansContactInfo;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
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

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final LoansContactInfo loansContactInfo;


    @GetMapping("/api/v1/info")
    public ResponseEntity<Object> getInfo() {
        Map<String, Object> body = new HashMap<>();
        body.put("Message", "Responding from loan microservice.");
        body.put("Build version", buildVersion);
        body.put("Java version", environment.getProperty("java.version"));
        body.put("Contact info ", loansContactInfo);

        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(body);
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
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
    public ResponseEntity<LoanResponseModel> fetchLoanDetails(@RequestHeader(ConstantsCatalog.SERVICE_HEADER_CORRELATION_ID) String correlationId,
                                                              @PathVariable
                                                                @Pattern(regexp="(^$|[0-9]{10})",
                                                                        message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        log.debug("{} found on GET /loans/api/v1/{mobileNumber}: {} ", ConstantsCatalog.SERVICE_HEADER_CORRELATION_ID, correlationId);

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
    public ResponseEntity<LoanResponseModel> createLoan(@PathVariable
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
            description = "REST API to update Customer &  Loan details based on a account number"
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
    public ResponseEntity<LoanResponseModel> updateLoanDetails(@PathVariable
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
    public ResponseEntity<ResponseBasicModel> deleteLoanDetails(@PathVariable
                                                                       @NotEmpty(message = "Customer mobile number must not be blank")
                                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                       String mobileNumber) {
        service.delete(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseBasicModel(SuccessCatalog.STATUS_200.getCode(), SuccessCatalog.STATUS_200.getMessage()));
    }
}
