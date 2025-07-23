package com.example.Youtube.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uk_user_video", columnNames = {"user_id", "video_id"}
))
public class Feeling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Enumerated(EnumType.STRING)
    private EnumFeel feeling;

}
