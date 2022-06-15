package com.britr.simpleRestaurant.controller;

import com.britr.simpleRestaurant.dto.AuthenticationRequest;
import com.britr.simpleRestaurant.dto.AuthenticationResponse;
import com.britr.simpleRestaurant.model.UserDetail;
import com.britr.simpleRestaurant.repository.UserDetailRepository;
import com.britr.simpleRestaurant.service.UserService;
import com.britr.simpleRestaurant.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) throws Exception{
        try{
            System.out.println("Eneterd controller"+request);
            Boolean valid = false;
            UserDetail detail = userDetailRepository.findByUsername(request.getUsername());
            if(bCryptPasswordEncoder.matches(request.getPassword(),detail.getPassword())){
                valid = true;
                System.out.println(detail);
            }

        }catch(Exception ex){
            throw new BadCredentialsException("Invalid credentials::",ex);
        }
        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt(jwtUtil.generateToken(userDetails));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
