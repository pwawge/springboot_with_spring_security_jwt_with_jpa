package com.auth.service;

import com.auth.model.LoginUser;
import com.auth.model.Role;
import com.auth.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsernameTset(){
        Set<Role > roles = new HashSet<>();
        roles.add(new Role(1,"ADMIN","Admin role"));
        when(userRepository.findByUserName("foo")).thenReturn(Optional.of(new LoginUser( 1,  "foo",  "foo",  true,roles )));
        Optional<LoginUser> loginUser = userRepository.findByUserName("foo");
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("foo");

        assertEquals("foo", userDetails.getUsername());
        assertEquals("foo",userDetails.getPassword());

    }

}
