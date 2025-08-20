package com.river.demo.dto;


import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String designation;
    String company;
    String address;
    String status;
}
