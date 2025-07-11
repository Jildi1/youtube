package com.example.Youtube.Service;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.Video;
import com.example.Youtube.Repository.ChannelRepository;
import com.example.Youtube.Repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VideoService {

    @Value("${path.video}")
    private String path;

    private final VideoRepository videoRepository;
    private final ChannelRepository channelRepository;

    public VideoService(VideoRepository videoRepository, ChannelRepository channelRepository) {
        this.videoRepository = videoRepository;
        this.channelRepository = channelRepository;
    }

    public Video uploadVideo(String title, String description, MultipartFile file, Channel channel) throws Exception {
        if(channel == null){
            throw new IllegalStateException();
        }

        if(file == null){
            throw new NullPointerException("File is null");
        }


        int index = file.getOriginalFilename().lastIndexOf('.');
        String extension = "";
        if (index > 0) {
            extension = file.getOriginalFilename().substring(index);
        }
        if(!extension.equals(".mp4") && !extension.equals(".mov") && !extension.equals(".avi") && !extension.equals("wmv")){
            throw new Exception("Invalid extension!");
        }

        Video video = new Video(title, description, channel);
        video.setExpansion(extension);
        String filename = UUID.randomUUID().toString().substring(0, 12);
        video.setPathName(filename);

        File targetFile = new File(path + File.separator + filename + extension);
        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        videoRepository.save(video);
        return video;
    }

    public Video findByPathName(String pathName){
        Video video = videoRepository.findByPathName(pathName);

        if(video == null){
            throw new NullPointerException("video is null!");
        }

        return video;
    }

    public void deleteVideo(String pathName, Channel channel) throws Exception {
        if(channel == null){
            throw new Exception("Channel not found!");
        }
        Video video = videoRepository.findByPathName(pathName);
        if(!video.getAuthor().equals(channel)){
            throw new SecurityException("You are not author of this video");
        }
        Files.delete(Path.of(path + File.separator + video.getPathName() + video.getExpansion()));
        videoRepository.delete(video);

    }
}
