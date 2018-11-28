package me.bactoria.celebbatch.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bactoria.celebbatch.channel.Channel;
import me.bactoria.celebbatch.channel.ChannelRepository;
import me.bactoria.celebbatch.channelLog.ChannelLog;
import me.bactoria.celebbatch.channelLog.ChannelLogRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static me.bactoria.celebbatch.CrawlChannel.channelLog;

@Slf4j
@Configuration
@AllArgsConstructor
public class ChannelLogJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ChannelRepository channelRepository;
    private final ChannelLogRepository channelLogRepository;

    @Bean
    public Job channelLogJob() {
        log.info("=================");
        log.info("channelLogJob Started");
        return jobBuilderFactory.get("channelLogJob")
                .start(channelLogJobStep(null, null))
                .build();
    }

    @Bean
    @JobScope
    public Step channelLogJobStep(@Value("#{jobParameters[requestDate]}") String requestDate, @Value("#{jobParameters[requestHour]}") Long requestHour) {
        return stepBuilderFactory.get("channelLogJobStep")
                .<Channel, ChannelLog>chunk(10)
                .reader(ChannelReader())
                .processor(ChannelProcessor(requestDate, requestHour))
                .writer(ChannelWriter())
                .build();
    }

    private ListItemReader<Channel> ChannelReader() {
        List<Channel> channelList = channelRepository.findAll();
        return new ListItemReader<>(channelList);
    }

    private ItemProcessor<Channel, ChannelLog> ChannelProcessor(String requestDate, Long requestHour) {
        return channel -> channelLog(channel, requestDate, requestHour);
    }

    private ItemWriter<ChannelLog> ChannelWriter() {
        return channelList -> channelLogRepository.saveAll(channelList);
    }

}
