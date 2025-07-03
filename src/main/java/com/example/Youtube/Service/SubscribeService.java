package com.example.Youtube.Service;

import com.example.Youtube.Model.User;
import com.example.Youtube.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class SubscribeService {

    private final UserRepository userRepository;

    @Autowired
    public SubscribeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void subscribe(User subscriber, Long authorId) throws Exception {
        Optional<User> author = userRepository.findById(authorId);
        if(!author.isPresent()){
            throw new UsernameNotFoundException("This author does not exist!");
        }
        if(subscriber.equals(author.get())){
            throw new Exception("You can't subscribe to yourself!");
        }
        if(author.get().getSubscribers().contains(subscriber)){
            throw new Exception("You already subscribe to that author!");
        }
        author.get().getSubscribers().add(subscriber);

        userRepository.save(author.get());
    }

    public void unsubscribe(User unsubscriber, Long authorId) throws Exception {
        Optional<User> author = userRepository.findById(authorId);
        if(!author.isPresent()){
            throw new UsernameNotFoundException("This author does not exist!");
        }
        if(unsubscriber.equals(author.get())){
            throw new Exception("You can't unsubscribe to yourself!");
        }
        if(!author.get().getSubscribers().contains(unsubscriber)){
            throw new Exception("You are not subscribed to this user!");
        }
        author.get().getSubscribers().remove(unsubscriber);

        userRepository.save(author.get());
    }


}
