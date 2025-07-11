package com.example.Youtube.Service;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.User;
import com.example.Youtube.Repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SubscribeService {

    private final ChannelRepository channelRepository;

    public SubscribeService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void subscribe(Channel subscriber, Long channelId) throws Exception {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if(!channel.isPresent()){
            throw new UsernameNotFoundException("This author does not exist!");
        }
        if(subscriber.equals(channel.get())){
            throw new Exception("You can't subscribe to yourself!");
        }
        if(channel.get().getSubscribers().contains(subscriber)){
            throw new Exception("You already subscribe to that author!");
        }
        channel.get().getSubscribers().add(subscriber);

        channelRepository.save(channel.get());
    }

    public void unsubscribe(Channel unsubscriber, Long channelId) throws Exception {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if(!channel.isPresent()){
            throw new UsernameNotFoundException("This author does not exist!");
        }
        if(unsubscriber.equals(channel.get())){
            throw new Exception("You can't unsubscribe to yourself!");
        }
        if(!channel.get().getSubscribers().contains(unsubscriber)){
            throw new Exception("You are not subscribed to this user!");
        }
        channel.get().getSubscribers().remove(unsubscriber);

        channelRepository.save(channel.get());
    }


}
