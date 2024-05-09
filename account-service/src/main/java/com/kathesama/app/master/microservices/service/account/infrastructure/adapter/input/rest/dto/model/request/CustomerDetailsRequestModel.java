package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.dto.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.request.CardRequestModel;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.request.LoanRequestModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerRequestModel",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDetailsRequestModel {
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tutor@eazybytes.com"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountRequestModel accounts;

    @Schema(
            description = "Account details of the Customer"
    )
    private CardRequestModel cards;

    @Schema(
            description = "Account details of the Customer"
    )
    private LoanRequestModel loans;
}
