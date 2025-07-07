package com.example.Youtube.Controller;

import com.example.Youtube.Model.AuthenticationRequest;
import com.example.Youtube.Model.AuthenticationResponse;
import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.JwtService;
import com.example.Youtube.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class AuthController {

    private final JwtService jwtService;

    private final UserService userService;
    
    @Autowired
    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            final String jwt = jwtService.createJwtToken(authenticationRequest);
            return ResponseEntity.ok().body(new AuthenticationResponse(jwt));
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

    @PostMapping("/signup")
    public ResponseEntity<?> registration(@RequestBody User user){
        try{
            userService.addUser(user);
            return ResponseEntity.ok().body("User is create!");
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

}
