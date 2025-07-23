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

    public Channel createChannel(User user, String name, String customId) throws Exception {
        if(user == null){
            throw new NullPointerException();
        }

        Channel channelFromDB = channelRepository.findByCustomId(customId);
        if(channelFromDB != null){
            throw new Exception("This customId is busy");
        }
        if(user.getChannels().size() == 100){
            throw new Exception("You have too many channels");
        }
        Channel channel = new Channel();
        channel.setVerified(true);
        channel.setCustomId(customId);
        channel.setAccount(user);
        channel.setChannelName(name);
        user.setCurrentChannel(channel);
        user.getChannels().add(channel);
        channelRepository.save(channel);
        return channel;
    }

    public void verifyChannel(){

    }
}
