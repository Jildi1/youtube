package com.example.Youtube.Service;

import com.example.Youtube.Model.User;
import com.example.Youtube.Model.Video;
import com.example.Youtube.Repository.VideoRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {

    @Value("${path.video}")
    private String path;

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void uploadVideo(Video video, MultipartFile file) throws Exception {
        if(file == null){
            throw new NullPointerException("File is null");
        }


        int index = file.getOriginalFilename().lastIndexOf('.');
        String extension = "";
        if (index > 0) {
            extension = file.getOriginalFilename().substring(index);
        }

        if(!extension.equals(".mp4") || !extension.equals(".mov") || !extension.equals(".avi") || !extension.equals("wmv")){
            throw new Exception("Invalid extension!");
        }

        video.setExpansion(extension);
        String filename = UUID.randomUUID().toString().substring(0, 12);
        video.setPathName(filename);

        File targetFile = new File(path + File.separator + filename + extension);
        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        videoRepository.save(video);
    }

    public Video findByPathName(String pathName){
        Video video = videoRepository.findByPathName(pathName);

        if(video == null){
            throw new NullPointerException("video is null!");
        }

        return video;
    }

    public void deleteVideo(String pathName, User user) throws IOException {

        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        Video video = videoRepository.findByPathName(pathName);
        if(!video.getAuthor().equals(user)){
            throw new SecurityException("You are not author of this video");
        }
        Files.delete(Path.of(path + File.separator + video.getPathName() + video.getExpansion()));
        videoRepository.delete(video);

    }
}
