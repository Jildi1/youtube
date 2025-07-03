package com.example.Youtube.Controller;

import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.SubscribeService;
import com.example.Youtube.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class SubscribeController {
    
    private final SubscribeService subscribeService;

    private final UserService userService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService, UserService userService) {
        this.subscribeService = subscribeService;
        this.userService = userService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal User subscriber, @RequestParam("authorId") Long authorId)throws Exception {
        try{
            subscribeService.subscribe(subscriber, authorId);
            Optional<User> author = userService.findById(authorId);
            return ResponseEntity.badRequest().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message("You subscribe to author: " + author.get().getUsername())
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
                            .status(HttpStatus.FORBIDDEN)
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .build()
            );


        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal User unsubscriber, @RequestParam("authorId") Long authorId) throws Exception {
        try {
            subscribeService.unsubscribe(unsubscriber, authorId);
            Optional<User> author = userService.findById(authorId);

            return ResponseEntity.badRequest().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message("You unsubscribe to author: " + author.get().getUsername())
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
                            .status(HttpStatus.FORBIDDEN)
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .build()
            );
        }
    }   

}
