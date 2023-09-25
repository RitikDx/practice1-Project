package com.practice1.controller;

import com.practice1.Dto.LoginDto;
import com.practice1.Dto.SignupDto;
import com.practice1.config.JWTAuthResponse;
import com.practice1.config.JWTTokenProvider;
import com.practice1.entity.User;
import com.practice1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProvider tokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;


    // http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenProvider.generateToken(userDetails);

        // Create a response with the JWT token
        JWTAuthResponse authResponse = new JWTAuthResponse(token);

        return ResponseEntity.ok(authResponse);
    }

    // http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){

        Boolean isExistEmail = userRepository.existsByEmail(signupDto.getEmail());
        if(isExistEmail){
            return new ResponseEntity<>("Email is already registered", HttpStatus.BAD_REQUEST);
        }

        Boolean isExistsUsername =  userRepository.existsByUsername(signupDto.getUsername());
        if(isExistsUsername){
            return new ResponseEntity<>("Already registered username", HttpStatus.BAD_REQUEST);
        }


        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userRepository.save(user);



        return new ResponseEntity<>("User is registered", HttpStatus.CREATED);

    }
}
