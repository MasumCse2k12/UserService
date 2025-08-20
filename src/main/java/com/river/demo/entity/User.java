package com.river.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    String firstName;
    @Column(name = "last_name", nullable = false)
    String lastName;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;
    @Column(name = "designation", nullable = false)
    String designation;
    @Column(name = "company", nullable = false)
    String company;
    @Column(name = "address", nullable = false)
    String address;
    @Column(name = "status", nullable = false)
    String status;
}
