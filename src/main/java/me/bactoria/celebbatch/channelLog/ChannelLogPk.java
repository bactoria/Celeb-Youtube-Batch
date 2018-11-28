package me.bactoria.celebbatch.channelLog;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
@NoArgsConstructor
public class ChannelLogPk implements Serializable {

    @Column
    private String id;

    @Column
    private LocalDate date;

    @Column
    private Long hour;

    @Builder
    public ChannelLogPk(String id, LocalDate date, Long hour) {
        this.id = id;
        this.date = date;
        this.hour = hour;
    }
}
