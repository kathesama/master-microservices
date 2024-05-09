package com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "LoanEntity",
        description = "Schema to hold LoanEntity information"
)
@Data
public class LoanRequestModel {
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Schema(
            description = "Mobile Number of Customer", example = "4365327698"
    )
    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @NotEmpty(message = "LoanEntity Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    @Schema(
            description = "LoanEntity Number of the customer", example = "548732457654"
    )
    @JsonProperty("loanNumber")
    private String loanNumber;

    @NotEmpty(message = "LoanType can not be a null or empty")
    @Schema(
            description = "Type of the loan", example = "Home LoanEntity"
    )
    @JsonProperty("loanType")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount", example = "100000"
    )
    @JsonProperty("totalLoan")
    private Double totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total loan amount paid", example = "1000"
    )
    @JsonProperty("amountPaid")
    private Double amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    @JsonProperty("outstandingAmount")
    private Double outstandingAmount;
}
