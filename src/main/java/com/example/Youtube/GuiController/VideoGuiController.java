package com.example.Youtube.GuiController;

import com.example.Youtube.Model.EnumFeel;
import com.example.Youtube.Model.User;
import com.example.Youtube.Model.Video;
import com.example.Youtube.Service.FeelingService;
import com.example.Youtube.Service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class VideoGuiController {
    @Value("${path.video}")
    private String path;

    private final VideoService videoService;
    private final FeelingService feelingService;

    public VideoGuiController(VideoService videoService, FeelingService feelingService) {
        this.videoService = videoService;
        this.feelingService = feelingService;
    }

    @GetMapping("/watch")
    public String watch(@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : #this") User user, @RequestParam("v") String pathName, Model model) throws Exception {
        try {
            Video video = videoService.findByPathName(pathName);
            String videoPath = "/video/" + video.getPathName() + video.getExpansion();
            model.addAttribute("videoTitle", video.getTitle());
            model.addAttribute("videoDescription", video.getDescription());
            model.addAttribute("videoUrl", videoPath);
            model.addAttribute("channelName", video.getAuthor().getChannelName());
            model.addAttribute("subscribersCount", video.getAuthor().getSubscribers().stream().count());
            if (user != null) {
                model.addAttribute("isSubscribed", video.getAuthor().getSubscribers().contains(user.getCurrentChannel()));
                EnumFeel feel = feelingService.getUserFeeling(user.getCurrentChannel(), pathName);
                model.addAttribute("currentFeeling", feel != null ? feel.name() : null);
            }
            model.addAttribute("videoPathName", pathName);
            model.addAttribute("likesCount", feelingService.countLikeByVideo(pathName));
            return "watchVideo";
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
