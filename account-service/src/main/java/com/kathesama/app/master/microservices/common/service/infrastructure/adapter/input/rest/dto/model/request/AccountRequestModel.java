package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.input.rest.dto.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "AccountRequestModel",
        description = "Schema to hold Account information"
)
public class AccountRequestModel {
    @NotNull(message = "AccountNumber can not be null")
    @Digits(integer=10, fraction=0, message = "AccountNumber must be 10 digits")
    @Schema(
            description = "Account Number of Eazy Bank account", example = "3454433243"
    )
    @JsonProperty("accountNumber")
    private Long accountNumber;

    @NotEmpty(message = "AccountType can not be a null or empty")
    @Schema(
            description = "Account type of Eazy Bank account", example = "Savings"
    )
    @JsonProperty("accountType")
    private String accountType;

    @NotEmpty(message = "BranchAddress can not be a null or empty")
    @Schema(
            description = "Eazy Bank branch address", example = "123 NewYork"
    )
    @JsonProperty("branchAddress")
    private String branchAddress;
}
