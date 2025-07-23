package com.example.Youtube.Model.DTO;

import com.example.Youtube.Model.EnumFeel;
import lombok.Data;

@Data
public class FeelingDTO {
    private String videoPathName;
    private EnumFeel type;

    public EnumFeel getType() {
        return type;
    }

    public String getVideoPathName() {
        return videoPathName;
    }

    public void setType(EnumFeel type) {
        this.type = type;
    }

    public void setVideoPathName(String videoPathName) {
        this.videoPathName = videoPathName;
    }
}
