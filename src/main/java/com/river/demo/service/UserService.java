package com.river.demo.service;

import com.river.demo.dto.*;
import com.river.demo.entity.User;
import com.river.demo.repository.UserRepository;
import com.river.demo.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ApiResponse registerUser(UserRegistrationRequest request) {
        log.info("User registration request :: {}", request);

        try {
            User userEo = buildUser(request);
            userRepository.save(userEo);
            log.info("SuccessfullySaved UserInfo. ID :: {}", userEo.getId());

            return ApiResponse.builder()
                    .code(HttpStatus.OK.name())
                    .message("SUCCESSFUL")
                    .build();
        } catch (Exception ex) {
            log.error("Failed to register user due internal server error. Cause :: {}", ex.getMessage());
            return ApiResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("SUCCESSFUL")
                    .build();
        }

    }


    public UserListResponse searchUser(UserSearchRequest userDto) {
        log.info("Searching User request param :: ", userDto);
        Integer size = userDto.getSize() != null ? userDto.getSize() : Constants.PAGE_SIZE;
        Integer offset = userDto.getOffset() == null ? 0 : userDto.getOffset();

        Pageable pageable = PageRequest.of(offset, size);

        Page<UserDto> userList = userRepository.searchUser(userDto.getEmail(),
                userDto.getFirstName(), userDto.getLastName(),
                userDto.getPhoneNumber(), userDto.getDesignation(),
                userDto.getCompany(), userDto.getAddress(), pageable);

        return UserListResponse.builder()
                .userList(userList.getContent())
                .build();
    }

    public UserDto findUserByEmail(String email) {
        log.info("Finding User by email :: {}", email);

        User user =  userRepository.findUserByEmail(email).orElse(null);

        return user == null ? null :
                UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .company(user.getCompany())
                .designation(user.getDesignation())
                .build();
    }

    private User buildUser(UserRegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .company(request.getCompany())
                .address(request.getAddress())
                .designation(request.getDesignation())
                .status(Constants.ACTIVE) // Default active
                .build();
    }
}
