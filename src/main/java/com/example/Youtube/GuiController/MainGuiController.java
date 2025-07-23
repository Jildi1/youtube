package com.example.Youtube.GuiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainGuiController {

    @GetMapping
    public String main(){
        return "main";
    }
}
