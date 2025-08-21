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


    @Query("""
       SELECT new com.river.demo.dto.UserDto(u.firstName, u.lastName, u.email,
       u.phoneNumber, u.designation, u.company, u.address, u.status)
       FROM User u
       WHERE 1=1
         AND (:email IS NULL OR lower(u.email) LIKE concat('%', lower(:email), '%'))
         AND (:firstName IS NULL OR lower(u.firstName) LIKE concat('%', lower(:firstName), '%'))
         AND (:lastName IS NULL OR lower(u.lastName) LIKE concat('%', lower(:lastName), '%'))
         AND (:phone IS NULL OR u.phoneNumber LIKE concat('%', :phone, '%'))
         AND (:designation IS NULL OR u.designation LIKE concat('%', :designation, '%'))
         AND (:company IS NULL OR u.company LIKE concat('%', :company, '%'))
         AND (:address IS NULL OR u.address LIKE concat('%', :address, '%'))
         AND u.status = 'ACTIVE'
       """)
    Page<UserDto> searchUser(
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phone") String phone,
            @Param("designation") String designation,
            @Param("company") String company,
            @Param("address") String address,
            Pageable pageable
    );

}
