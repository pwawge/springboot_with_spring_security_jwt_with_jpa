package com.auth.service;

import com.auth.dto.CustomUserDetails;
import com.auth.model.LoginUser;
import com.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginUser> loginUser = userRepository.findByUserName(username);
        loginUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return loginUser.map(CustomUserDetails::new).get();
    }
}
