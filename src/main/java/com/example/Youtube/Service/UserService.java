package com.example.Youtube.Service;

import com.example.Youtube.Model.User;
import com.example.Youtube.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User in null!");
        }

        return user;
    }

    public void addUser(User user) throws Exception {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            throw new Exception(); // edit exception
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
