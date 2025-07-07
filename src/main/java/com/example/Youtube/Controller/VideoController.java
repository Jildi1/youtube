package com.example.Youtube.Controller;

import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Model.Video;
import com.example.Youtube.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
public class VideoController {



    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping("/upload-video")
    public ResponseEntity<?> uploadVideo(@AuthenticationPrincipal User user,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("file") MultipartFile file) throws Exception {
        try {
            Video video = new Video(title, description, user);
            videoService.uploadVideo(video, file);
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

    @PostMapping("/delete")
    public ResponseEntity<?> deleteVideo(@AuthenticationPrincipal User user, @RequestParam("pathName") String pathName){
        try{
            videoService.deleteVideo(pathName, user);
            return ResponseEntity.badRequest().body(
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
