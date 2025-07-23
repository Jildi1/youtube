package com.example.Youtube.Controller;

import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Model.Video;
import com.example.Youtube.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

// три метода на создание удаления просмотра видео
@RestController
@RequestMapping("/api")
public class VideoController {



    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping("/upload-video")
    public ResponseEntity<?> uploadVideo(
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("file") MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            Video video = videoService.uploadVideo(title, description, file, user.getCurrentChannel());
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(new Date())
                            .message("Video is uploaded! His pathName: " + video.getPathName())
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
    }

    @PostMapping("/watch")
    public ResponseEntity<?> watchVideo(@RequestParam("v") String pathName){
        try{
            Video video = videoService.findByPathName(pathName);
            return ResponseEntity.ok().body(
                    "Your are watching " + pathName +
                    ", Expansion" + video.getExpansion() +
                    ", Author: " + video.getAuthor() +
                    ", Title: " + video.getTitle() +
                    ", Description: " + video.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
    }

    @PostMapping("/delete-video")
    public ResponseEntity<?> deleteVideo(@RequestParam("pathName") String pathName){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            videoService.deleteVideo(pathName, user.getCurrentChannel());
            return ResponseEntity.ok().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message("You delete video!")
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .build()
            );
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
    }



}
