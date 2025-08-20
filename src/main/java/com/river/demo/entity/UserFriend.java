package com.river.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_friend")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFriend extends BaseEntity {

    @Column(name = "sender", nullable = false)
    Integer sender;
    @Column(name = "receiver", nullable = false)
    Integer receiver;
    @Column(name = "request_status", nullable = false)
    String requestStatus;
}
