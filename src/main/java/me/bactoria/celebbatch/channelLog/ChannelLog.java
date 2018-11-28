package me.bactoria.celebbatch.channelLog;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Entity
@ToString
@NoArgsConstructor
public class ChannelLog {

    @EmbeddedId
    private ChannelLogPk ChannelLogPk;

    @Column
    private Long subscriber;

    @Builder
    public ChannelLog(me.bactoria.celebbatch.channelLog.ChannelLogPk channelLogPk, Long subscriber) {
        ChannelLogPk = channelLogPk;
        this.subscriber = subscriber;
    }
}
