package com.example.Youtube.Controller;

import com.example.Youtube.Model.User;
import com.example.Youtube.Service.SubscribeService;
import com.example.Youtube.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.ok().body("You have subscribed to the user " + author.get().getUsername());
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal User unsubscriber, @RequestParam("authorId") Long authorId) throws Exception {
        try {
            subscribeService.unsubscribe(unsubscriber, authorId);
            Optional<User> author = userService.findById(authorId);

            return ResponseEntity.ok().body("You have unsubscribed from the user " + author.get().getUsername());
        } catch (Exception e){
            throw new Exception(e);
        }
    }   

}
