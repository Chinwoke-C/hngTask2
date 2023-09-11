package com.hngTask2.identify.data.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hngTask2.identify.data.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.hngTask2.identify.utility.IdentifyUtilities.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PersonRequest {


    @JsonProperty("full_name")
    private String name;
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    @Pattern(regexp = "^(0[1-9]|1[012])[- /.] (0[1-9]|[12][0-9]|3[01])[- /.] (19|20)\\d\\d$",
    message = INVALID)
    private String dateOfBirth;
    @Pattern(regexp = "((^234)[0–9]{10})", message = INVALID_PHONE_NUMBER)
    private String phoneNumber;
   @JsonUnwrapped
   @NotBlank(message = NOT_BLANK)
   @NotNull(message = NOT_NULL)
    private Address address;
}
