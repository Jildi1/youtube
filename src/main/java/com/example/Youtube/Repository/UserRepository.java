package com.example.Youtube.Repository;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
