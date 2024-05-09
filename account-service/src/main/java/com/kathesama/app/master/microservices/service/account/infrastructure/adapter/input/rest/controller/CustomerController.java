package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.master.microservices.service.account.application.ports.input.AccountFetchServiceInputPort;
import com.kathesama.app.master.microservices.service.account.application.ports.input.AccountServiceInputPort;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.response.CustomerDetailsResponse;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.AccountRestMapper;
import com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper.CustomerDetailsRestMapper;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Katherine Aguirre
 */

@Tag(
        name = "CRUD REST APIs for Customer in EazyBank",
        description = "CRUD REST APIs in EazyBank to FETCH customer details"
)
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {
    private final AccountFetchServiceInputPort customerService;

    private final CustomerDetailsRestMapper customerMapper;

    @Operation(
            summary = "Fetch customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
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
    public ResponseEntity<CustomerDetailsResponse> fetchCustomerDetails(@PathVariable
                                                                    @Pattern(regexp="(^$|[0-9]{10})",
                                                                            message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(
                customerMapper.toCustomerDetailsResponse(
                        customerService.fetchCustomerDetails(mobileNumber)
                )
        );
    }
}
