package com.auth.controller;

import com.auth.service.CustomUserDetailsService;
import com.auth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/dashboard")
    public ResponseEntity<?> openDashboard(){
        Map<String,Object> responseMap = new  HashMap<String,Object>();
        responseMap.put("Message","Hello , Buddy");
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }
    @PostMapping(value = "/authenticate",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody Map<String,String> credential) {
        Map<String,Object> responseMap = new  HashMap<String,Object>();
        try {
            final Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.get("username").toString(), credential.get("password")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (BadCredentialsException ex){
            responseMap.put("Message","Your password or username is incorrect");
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
       final UserDetails userDetails =  customUserDetailsService.loadUserByUsername(credential.get("username"));
         final String jwtToken = jwtUtil.generateToken(userDetails);

        responseMap.put("Token","Bearer "+jwtToken);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
