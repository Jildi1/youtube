package com.example.Youtube.Service;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.User;
import com.example.Youtube.Repository.ChannelRepository;
import com.example.Youtube.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ChannelRepository channelRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ChannelRepository channelRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User in null!");
        }

        return user;
    }
    @Transactional
    public void addUser(User user) throws Exception {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if(userFromDB != null){
            throw new Exception("User with this username is already exist"); // edit exception
        }

        Channel channel = new Channel();
        channel.setChannelName(user.getUsername());
        channel.setAccount(user);
        channel.setVerified(false);
        channel.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getChannels().add(channel);
        user.setCurrentChannel(channel);
        userRepository.save(user);
        channelRepository.save(channel);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
}
