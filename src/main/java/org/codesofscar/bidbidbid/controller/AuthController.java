package org.codesofscar.bidbidbid.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codesofscar.bidbidbid.dto.UserDto;
import org.codesofscar.bidbidbid.model.Users;
import org.codesofscar.bidbidbid.serviceImpl.UserServiceImpl;
import org.codesofscar.bidbidbid.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserServiceImpl userService;

    private PasswordEncoder passwordEncoder;

    private JwtUtils utils;

    @Autowired
    public AuthController(UserServiceImpl userService, PasswordEncoder passwordEncoder, JwtUtils utils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        Users user = userService.saveUser(userDto);
        UserDto userDto1= new ObjectMapper().convertValue(user, UserDto.class);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody UserDto userDto) {
        UserDetails user = userService.loadUserByUsername(userDto.getUsername());
        if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            String jwtToken = utils.createJwt.apply(user);
            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Username and Password is incorrect", HttpStatus.CREATED);
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome to your Dashboard";
    }
}
