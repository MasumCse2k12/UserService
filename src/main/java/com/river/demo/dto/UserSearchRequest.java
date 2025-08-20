package com.river.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserSearchRequest {
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String designation;
    String company;
    String address;
    String status;

    @Size(max = 999)
    Integer size;
    Integer offset;

}
