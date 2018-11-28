package me.bactoria.celebbatch.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bactoria.celebbatch.channel.Channel;
import me.bactoria.celebbatch.channel.ChannelRepository;
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

import static me.bactoria.celebbatch.CrawlChannel.channelUpdate;

@Slf4j
@Configuration
@AllArgsConstructor
public class ChannelUpdateJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ChannelRepository channelRepository;

    @Bean
    public Job channelUpdateJob() {
        log.info("=================");
        log.info("channelUpdateJob Started");
        return jobBuilderFactory.get("channelUpdateJob")
                .start(channelUpdateJobStep(null))
                .build();
    }


    @Bean
    @JobScope
    public Step channelUpdateJobStep(@Value("#{jobParameters[requestTime]}") String requestTime) {
        return stepBuilderFactory.get("channelUpdateJobStep")
                .<Channel, Channel>chunk(10)
                .reader(ChannelReader())
                .processor(ChannelProcessor())
                .writer(ChannelWriter())
                .build();
    }

    private ListItemReader<Channel> ChannelReader() {
        List<Channel> channelList = channelRepository.findAll();
        return new ListItemReader<>(channelList);
    }

    private ItemProcessor<Channel, Channel> ChannelProcessor() {
        return channel -> channelUpdate(channel);
    }

    private ItemWriter<Channel> ChannelWriter() {
        return channelList -> channelRepository.saveAll(channelList);
    }

}
