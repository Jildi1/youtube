package com.example.Youtube.Service;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.User;
import com.example.Youtube.Repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void changeChannel(User user, Long channelId){
        Optional<Channel> channel = channelRepository.findById(channelId);
        if(channel == null){
            throw new NullPointerException();
        }
        if(!channel.get().getAccount().equals(user)){
            throw new SecurityException();
        }
        user.setCurrentChannel(channel.get());
    }

    public Optional<Channel> findById(Long id){
        return channelRepository.findById(id);
    }
}
