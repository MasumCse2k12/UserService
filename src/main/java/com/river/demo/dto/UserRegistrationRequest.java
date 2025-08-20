package com.river.demo.dto;

import com.river.demo.utils.PhoneNumberValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistrationRequest implements Serializable {
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String email;
    @NotNull
    @Size(max = 14)
    @PhoneNumberValidator
    String phoneNumber;
    @NotNull
    String designation;
    @NotNull
    String company;
    @NotNull
    String address;
    @NotNull
    String status;
}
