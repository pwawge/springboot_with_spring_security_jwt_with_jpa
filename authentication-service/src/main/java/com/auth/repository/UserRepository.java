package com.auth.repository;

import com.auth.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<LoginUser,Integer> {
    Optional<LoginUser> findByUserName(String userName);
}
