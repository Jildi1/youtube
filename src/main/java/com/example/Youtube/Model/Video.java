package com.example.Youtube.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;

    private String description;

    private String pathName;

    private String expansion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id")
    private Channel author;

    public Video(String title, String description, Channel author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }
}
