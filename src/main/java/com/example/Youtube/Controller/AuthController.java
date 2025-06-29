package com.example.Youtube.Controller;

import com.example.Youtube.Model.AuthenticationRequest;
import com.example.Youtube.Model.AuthenticationResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.JwtService;
import com.example.Youtube.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            final String jwt = jwtService.createJwtToken(authenticationRequest);
            return ResponseEntity.ok().body(new AuthenticationResponse(jwt));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registration(@RequestBody User user){
        try{
            userService.addUser(user);
            return ResponseEntity.ok().body("User is create!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
