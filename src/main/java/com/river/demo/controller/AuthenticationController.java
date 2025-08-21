package com.river.demo.controller;

import com.river.demo.dto.ApiResponse;
import com.river.demo.dto.LoginRequest;
import com.river.demo.dto.UserDto;
import com.river.demo.service.UserService;
import com.river.demo.utils.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user/")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final RedisTemplate redisTemplate;
    private final UserService userService;

    public AuthenticationController(RedisTemplate redisTemplate,
                                    UserService userService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        log.info("Getting login request from {}", request.getEmail());

      try {
          String otp = generateOtp();
          // send OTP the  requested email asynchronus way
          //store the otp into user email key
          redisTemplate.opsForValue().set(request.getEmail(), otp, 120, TimeUnit.SECONDS);

      } catch (Exception e) {
          return ResponseEntity.ok(ApiResponse.builder()
                  .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                  .message("Internal Server error. Please try again later.")
                  .build());
      }


        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.OK.name())
                .message("Sent OTP to your email address!")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate/otp")
    public ResponseEntity<ApiResponse> validateOtp(@Valid @RequestBody LoginRequest request) {
        String otp = request.getOtp();
        log.info("Getting OTP :: {}", otp);

        if(!StringUtils.hasText(otp)) {
            return ResponseEntity.ok(ApiResponse.builder()
                            .code(HttpStatus.BAD_REQUEST.name())
                            .message("OTP is required")
                    .build());
        }


        String storedOtp = redisTemplate.opsForValue().get(request.getEmail()) != null ?
                redisTemplate.opsForValue().get(request.getEmail()).toString() : null;
       String token = "";
       try {
           if(request.getOtp().equals(storedOtp)) {
               //check user exist
               UserDto user = userService.findUserByEmail(request.getEmail());
               //generate auth token
               token = generateJwtToken(request.getEmail(), Constants.JWT_TOKEN_VALIDITY, jwtSecret);
           } else {
                //OTP expired or invalid
               ApiResponse response = ApiResponse.builder()
                       .code(HttpStatus.BAD_REQUEST.name())
                       .message("Invalid OTP!")
                       .build();
               return ResponseEntity.ok(response);
           }
       } catch (Exception ex) {

       }

        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.OK.name())
                .message("Validate OTP!")
                .token(token)
                .build();
        return ResponseEntity.ok(response);
    }

    private  String generateJwtToken(String subject, long validityInMinutes, String secret) {
        // Generate a secure key for signing the JWT
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        // Set claims (payload)
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_role", "user"); // Example custom claim

        // Set token expiration time
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + TimeUnit.MINUTES.toMillis(validityInMinutes));

        // Build and sign the JWT
        String jwt = Jwts.builder()
                .setClaims(claims) // Custom claims
                .setSubject(subject) // Subject of the token (e.g., username)
                .setIssuedAt(now) // When the token was issued
                .setExpiration(expiration) // When the token expires
                .signWith(key, SignatureAlgorithm.HS256) // Sign with the generated key and algorithm
                .compact(); // Compact the token into a string

        return jwt;
    }

    private String generateOtp() {
        Random random = new Random();
        return String.valueOf(random.nextInt(999999)); // 6 digits OTP
    }
}
