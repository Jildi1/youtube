package com.example.Youtube.Controller;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

//    @PostMapping("/changeChannel")
//    public ResponseEntity<?> changeChannel(@AuthenticationPrincipal User user,
//                                           @RequestParam("channelId") Long channelId){
//        try{
//            channelService.changeChannel(user, channelId);
//            Optional<Channel> channel = channelService.findById(channelId);
//            return ResponseEntity.badRequest().body(
//                    HttpResponse
//                            .builder()
//                            .timeStamp(new Date())
//                            .message("You in a channel " + channel.get().getChannelName())
//                            .status(HttpStatus.ACCEPTED)
//                            .statusCode(HttpStatus.ACCEPTED.value())
//                            .build()
//            );
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(
//                    HttpResponse
//                            .builder()
//                            .timeStamp(new Date())
//                            .message(e.getMessage())
//                            .status(HttpStatus.BAD_REQUEST)
//                            .statusCode(HttpStatus.BAD_REQUEST.value())
//                            .build()
//            );
//        }
//    }

//    @PostMapping("createChannel")
//    public ResponseEntity<?> createChannel(@AuthenticationPrincipal User user,
//                                           @RequestParam("channelName") String name,
//                                           @RequestParam("customId") String customId){
//
//        try{
//
//        } catch (Exception e)
//        return ResponseEntity.badRequest().body(
//                HttpResponse
//                        .builder()
//                        .timeStamp(new Date())
//                        .message(e.getMessage())
//                        .status(HttpStatus.BAD_REQUEST)
//                        .statusCode(HttpStatus.BAD_REQUEST.value())
//                        .build()
//        );
//    }


}
