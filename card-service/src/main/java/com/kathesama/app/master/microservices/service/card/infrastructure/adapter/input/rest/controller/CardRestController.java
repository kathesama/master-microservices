package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.master.microservices.service.card.util.CardsContactInfo;
import com.kathesama.app.master.microservices.service.card.application.ports.input.CardServiceInputPort;
import com.kathesama.app.master.microservices.service.card.domain.model.Card;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.dto.model.request.CardRequestModel;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.mapper.CardRestMapper;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ErrorResponseDto;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.ResponseBasicModel;
import com.kathesama.app.master.microservices.service.common.util.common.SuccessCatalog;
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
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardRestController {
    private final CardServiceInputPort service;
    private final CardRestMapper mapper;

    @Value("${build.version}")
    private String buildVersion;

    private final Environment environment;

    private final CardsContactInfo cardsContactInfo;


    @GetMapping("/api/v1/info")
    public ResponseEntity<Object> getInfo() {
        Map<String, Object> body = new HashMap<>();
        body.put("Message", "Responding from card microservice.");
        body.put("Build version", buildVersion);
        body.put("Java version", environment.getProperty("java.version"));
        body.put("Contact info ", cardsContactInfo);

        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(body);
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
    public ResponseEntity<CardResponseModel> fetchAccountDetails(@PathVariable
                                                                @Pattern(regexp="(^$|[0-9]{10})",
                                                                        message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        Card card = service.fetch(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.toCardResponse(card)
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
    public ResponseEntity<CardResponseModel> createAccount(@PathVariable
                                                              @NotEmpty(message = "Customer mobile number must not be blank")
                                                              @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                              String mobileNumber) {
        return ResponseEntity
            .status(Integer.parseInt(SuccessCatalog.STATUS_200.getCode()))
            .body(
                mapper.toCardResponse(
                        service.createCard(mobileNumber)
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
    public ResponseEntity<CardResponseModel> updateAccountDetails(@PathVariable
                                                                    @NotEmpty(message = "Customer mobile number must not be blank")
                                                                    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                    String mobileNumber,
                                                                @Valid @RequestBody CardRequestModel cardRequestModel) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        mapper.toCardResponse(
                                service.update(mobileNumber, mapper.toCard(cardRequestModel))
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
