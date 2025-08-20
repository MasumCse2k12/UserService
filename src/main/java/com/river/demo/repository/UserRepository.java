package com.river.demo.repository;

import com.river.demo.dto.UserDto;
import com.river.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u WHERE u.email = :email and u.status = 'ACTIVE'")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT com.river.demo.UserDto(u.firstName) from User u WHERE 1 = 1 " +
            " and (:email is NULL OR lower(u.email) like concat('%'+:email + '%')  )" +
            " and (:firstName is NULL OR lower(u.firstName) like concat('%'+:firstName + '%') )" +
            " and (:lastName is NULL OR u.lastName like :lastName  )" +
            " and (:phone is NULL OR u.phone like :phone )" +
            " and (:designation is NULL OR u.designation like :phone )" +
            " and (:company is NULL OR u.company like :company)" +
            " and (:address is NULL OR u.address like :address )" +
            " and u.status = 'ACTIVE'")
    Page<UserDto> searchUser(@Param("email") String email,
                             @Param("firstName") String firstName,
                             @Param("lastName") String lastName,
                             @Param("phone") String phone,
                             @Param("designation") String designation,
                             @Param("company") String company,
                             @Param("address") String address,
                             Pageable pageable);
}
