package com.example.Youtube.Service;

import com.example.Youtube.Model.*;
import com.example.Youtube.Repository.FeelingRepository;
import jakarta.persistence.EnumType;
import org.springframework.stereotype.Service;

@Service
public class FeelingService {

    private final VideoService videoService;
    private final FeelingRepository feelingRepository;

    public FeelingService(VideoService videoService, FeelingRepository feelingRepository) {
        this.videoService = videoService;
        this.feelingRepository = feelingRepository;
    }

    public void feeling(User user, String pathName, EnumFeel type){
        Channel channel = user.getCurrentChannel();
        Video video = videoService.findByPathName(pathName);

        if(video == null){
            throw new IllegalArgumentException();
        }
        if(channel == null){
            throw new IllegalArgumentException();
        }

        Feeling feelingFromDB = feelingRepository.findByIds(channel.getId(), video.getId());
        // если существует -> 1 или 2
        if(feelingFromDB != null) {
            if(feelingFromDB.getFeeling().equals(type)) { // 1 - удаляем
                feelingRepository.deleteById(feelingFromDB.getId());
                return;
            }

            feelingFromDB.setFeeling(type); // 2 - изменяем
            feelingRepository.save(feelingFromDB);
            return;
        }
        //если не существует
        Feeling feeling = new Feeling();
        feeling.setChannel(channel);
        feeling.setVideo(video);
        feeling.setFeeling(type);
        feelingRepository.save(feeling);
    }


    public long countLikeByVideo(String videoId){
        return feelingRepository.countLikesByVideo(videoId);
    }

    public EnumFeel getUserFeeling(Channel channel, String pathName){
        return feelingRepository.getUserFeeling(channel, pathName);
    }
}
