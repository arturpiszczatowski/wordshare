package com.pjatk.wordshare.repository;

import com.pjatk.wordshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    org.springframework.security.core.userdetails.User getUserByEmail(String email);
}
