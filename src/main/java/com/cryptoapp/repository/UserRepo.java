package com.cryptoapp.repository;

import com.cryptoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByLogin(String login);

    User findByEmail(String email);
}
