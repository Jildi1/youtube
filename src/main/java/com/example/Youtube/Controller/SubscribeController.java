package com.example.Youtube.Controller;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.ChannelService;
import com.example.Youtube.Service.SubscribeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

// два метода на подписку или отписку
@RestController
@RequestMapping("/api")
public class SubscribeController {

    private final SubscribeService subscribeService;
    private final ChannelService channelService;

    public SubscribeController(SubscribeService subscribeService, ChannelService channelService) {
        this.subscribeService = subscribeService;
        this.channelService = channelService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam("channelId") Long channelId, @AuthenticationPrincipal User subscribe) throws Exception {
        try{
            subscribeService.subscribe(subscribe.getCurrentChannel(), channelId);
            Optional<Channel> channel = channelService.findById(channelId);
            return ResponseEntity.ok().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message("You subscribe to channel: " + channel.get().getChannelName())
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .build()
            );
        }
        catch (Exception e){
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

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam("channelId") Long channelId, @AuthenticationPrincipal User unsubscriber) throws Exception {
        try {
            subscribeService.unsubscribe(unsubscriber.getCurrentChannel(), channelId);
            Optional<Channel> channel = channelService.findById(channelId);
            return ResponseEntity.ok().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message("You unsubscribe to channel: " + channel.get().getChannelName())
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .build()
            );
        }
        catch (Exception e){
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
