package me.bactoria.celebbatch.channel;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String introVideoUrl;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String image;

    @Column
    private Long subscriber;

    @Column
    private LocalDate joinDate;

    @Column
    private Long views;

    @Column
    private LocalDateTime updatedTime;

    @Builder
    public Channel(String id, String name, String introVideoUrl, String title, String content, String image, Long subscriber, LocalDate joinDate, Long views, LocalDateTime updatedTime) {
        this.id = id;
        this.name = name;
        this.introVideoUrl = introVideoUrl;
        this.title = title;
        this.content = content;
        this.image = image;
        this.subscriber = subscriber;
        this.joinDate = joinDate;
        this.views = views;
        this.updatedTime = updatedTime;
    }
}
