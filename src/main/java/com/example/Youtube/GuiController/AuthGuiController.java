package com.example.Youtube.GuiController;

import com.example.Youtube.Model.AuthenticationRequest;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.JwtService;
import com.example.Youtube.Service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthGuiController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthGuiController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public String signin(@RequestParam("username") String username, @RequestParam("password") String password){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            return "redirect:/";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/signin")
    public String getLogin(){
        return "signin";
    }

    @GetMapping("/signup")
    public String getReg(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
