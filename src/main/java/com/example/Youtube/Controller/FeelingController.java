package com.example.Youtube.Controller;

import com.example.Youtube.Model.DTO.FeelingDTO;
import com.example.Youtube.Model.HttpResponse;
import com.example.Youtube.Model.User;
import com.example.Youtube.Service.FeelingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

// один метод на лайк или дизлайк
@Slf4j
@RestController
@RequestMapping("/api")
public class FeelingController {

    private final FeelingService feelingService;

    public FeelingController(FeelingService feelingService) {
        this.feelingService = feelingService;
    }

    @PostMapping("/feeling")
    public ResponseEntity<?> feeling(@RequestBody FeelingDTO feelingDTO, @AuthenticationPrincipal User user) {
        try {
            feelingService.feeling(user, feelingDTO.getVideoPathName(), feelingDTO.getType());
            return ResponseEntity.ok().body(
                    HttpResponse
                            .builder()
                            .timeStamp(new Date())
                            .message("success!")
                            .data(Map.of("likesCount", feelingService.countLikeByVideo(feelingDTO.getVideoPathName())))
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
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
}
