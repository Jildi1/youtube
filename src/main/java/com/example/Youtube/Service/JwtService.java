package com.example.Youtube.Service;


import com.example.Youtube.Model.AuthenticationRequest;
import com.example.Youtube.Model.User;
import com.example.Youtube.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public String createJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password!", e);
        }

        final User user = userService.loadUserByUsername(authenticationRequest.getUsername());
        return jwtUtil.generateToken(user);
    }
}
